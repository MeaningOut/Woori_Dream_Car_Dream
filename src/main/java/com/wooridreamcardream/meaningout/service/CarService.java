package com.wooridreamcardream.meaningout.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wooridreamcardream.meaningout.domain.Car;
import com.wooridreamcardream.meaningout.domain.Category;

import com.wooridreamcardream.meaningout.domain.Company;
import com.wooridreamcardream.meaningout.dto.*;
import com.wooridreamcardream.meaningout.dto.car.*;
import com.wooridreamcardream.meaningout.repository.CarRepository;
import com.wooridreamcardream.meaningout.repository.CategoryRepository;
import com.wooridreamcardream.meaningout.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CarService {
    private final CarRepository carRepository;
    private final CategoryRepository categoryRepository;
    private final CompanyRepository companyRepository;

    private final FlaskService flaskService;
    private final WooriHttpService wooriHttpService;
    private final CompanyService companyService;

    /**
     * findAll(), findById(Long id), findByIdIn(List<Long> ids) 모두 자동차의 데이터를 가지고 옵니다.
     * 데이터 (아이디, 모델 명, 세부 명, 차 가격(최소), 차 가격(최대), 회사 명, 엔진 형식, 배기량, 연비(등급), 승차인원, 구동방식, 변속기)
     * @return
     */
    @Transactional
    public List<CarResponseDto> findAll() {
        return carRepository.findAll().stream().map(CarResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public CarResponseDto findById(Long id) {
        Car entity = carRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 자동차가 없습니다. id = " + id));
        return new CarResponseDto(entity);
    }

    @Transactional
    public List<CarResponseDto> findByIdIn(List<Long> ids) {
        return carRepository.findByIdIn(ids).stream().map(CarResponseDto::new).collect(Collectors.toList());
    }

    /**
     *
     * 회사와 모델명이 존재하는 지 확인합니다.
     * 없으면 만듭니다.
     * 자동차 데이터를 저장합니다.
     *
     * @param carSaveRequestDto 저장하고자 하는 자동차 데이터
     * @return
     */
    @Transactional
    public Long save(CarSaveRequestDto carSaveRequestDto) {
        Company company = companyRepository.findByName(carSaveRequestDto.getCompanyName())
                .orElseGet(() -> companyRepository.save(new CompanySaveRequestDto(carSaveRequestDto.getCompanyName()).toEntity()));

        Category category = categoryRepository.findByName(carSaveRequestDto.getCategoryName())
                .orElseGet(() -> categoryRepository.save(new CategorySaveRequestDto(carSaveRequestDto.getCategoryName()).toEntity()));

        Car car = carRepository.findByCategoryIdAndName(category.getId(), carSaveRequestDto.getName())
                .orElseGet(() -> carRepository.save(carSaveRequestDto.toEntity(category, company)));

        return car.getId();
    }

    /**
     *
     * 1. 플라스크 추천 서버에 사용자 취향 정보 데이터 (소비신념)를 담아서 추천 자동차 요청을 보낸다.
     * 2. 사용자 연 소득, 추천 자동차 가격을 담아서 우리은행 신차 대출 조회 API 요청을 보낸다.
     * API 응답값은 자동차에 대한 사용자 대출 한도 금액이다.
     * 3. API로부터 돌아온 사용자 대출 한도 금액이 사용자가 입력한 대출 한도 범위 (minimum과 maximum 사이)내에 들어가는지 확인한다.
     * 4. 대출 한도 범위 내 자동차에 대해 회사 로고, 자동차 상세 정보를 데이터베이스로부터 가져온다.
     *
     * @param userIncome 사용자 연 소득
     * @param minimum 사용자 대출 한도 범위 (최소)
     * @param maximum 사용자 대출 한도 범위 (최대)
     * @param data 소비신념 (추천 시스템 요청 시 필요)
     * @return
     */
    @Transactional
    public List<CarWooriResponseDto> recommend(String userIncome, BigDecimal minimum, BigDecimal maximum, FlaskRequestDto data) {
        // 추천 시스템 요청
        CarPythonResponseDto[] list = flaskService.recommendedCars(data).block();

        List<CarWooriRequestDto> requestDtos = new ArrayList<>();
        Map<Long, String> similarityData = new HashMap<>();
        for (CarPythonResponseDto dto : list) {
            requestDtos.add(CarWooriRequestDto.builder()
                    .carId(Long.valueOf(dto.getId()))
                    .dataBody(RequestDataBody.builder()
                            .DBPE_ANL_ICM_AM(userIncome)
                            .CAR_PR(dto.getAvg_price())
                            .build())
                    .build());
            similarityData.put(Long.valueOf(dto.getId()), dto.getSimilarity());
        }

        List<Map<Long, String>> response = wooriHttpService.request(requestDtos);

        Map<Long, BigDecimal> loanData = checkedValidCar(response, minimum, maximum);

        return getValidCarDetails(loanData);
    }

    /**
     *
     * 우리은행 API 응답값을 읽어서 대출 한도 금액 (LN_AVL_AM)을 찾는다.
     * 대출 한도 금액이 사용자 대출 한도 범위 내에 있는 자동차의 자동차 id를 반환한다.
     *
     * @param cars 자동차 별 우리은행 API 응답값 (Long: 자동차 id, String: API 응답값)
     * @param minimum 사용자 대출 한도 범위 (최소)
     * @param maximum 사용자 대출 한도 범위 (최대)
     * @return
     */
    public Map<Long, BigDecimal> checkedValidCar(List<Map<Long, String>> cars, BigDecimal minimum, BigDecimal maximum) {
        Map<Long, BigDecimal> loanData = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        for (Map<Long, String> object: cars) {

            object.forEach((key, value)->
            {
                try {
                    Map<String, Object> m_ap = new HashMap<>();
                    m_ap = mapper.readValue(value, new TypeReference<Map<String, Object>>(){});
                    Map<String, String> m__ap = mapper.convertValue(m_ap.get("dataBody"), Map.class);

//                  API로부터 반환받은 대출 한도 금액(LN_AVL_AM)이 사용자가 입력한 대출 범위에 있는지 확인
                    BigDecimal LN_AVL_AM = new BigDecimal(m__ap.get("LN_AVL_AM"));
                    if (LN_AVL_AM.compareTo(minimum) > 0 && LN_AVL_AM.compareTo(maximum) < 0) {
                        loanData.put(key, LN_AVL_AM);
                    }

                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
        }
        return loanData;
    }

    public List<Long> checkedValidCarId(Map<Long, BigDecimal> cars) {
        return new ArrayList<>(cars.keySet());
    }

    /**
     *
     * 사용자 취향과 대출 한도 범위 내에 있는 자동차 id의 세부 정보를 가지고 온다.
     * 자동차 회사의 logo 이미지를 가지고 오기 위해 모든 CompanyService의 findByName을 사용했습니다.
     *
     * @param cars 자동차 id와 대출 한도 금액
     * @return
     */
    public List<CarWooriResponseDto> getValidCarDetails(Map<Long, BigDecimal> cars) {
        List<CarResponseDto> carList = findByIdIn(checkedValidCarId(cars));
        List<CarWooriResponseDto> carWooriResponseDtos = new ArrayList<>();

        for (CarResponseDto carResponseDto: carList) {
            CompanyResponseDto companyResponseDto = companyService.findById(carResponseDto.getCompany().getId());
            carWooriResponseDtos.add(new CarWooriResponseDto(carResponseDto, cars.get(carResponseDto.getId())));
        }
        return carWooriResponseDtos;
    }
}
