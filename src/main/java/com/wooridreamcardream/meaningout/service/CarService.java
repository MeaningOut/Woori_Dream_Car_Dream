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

    @Transactional
    public Long save(CarSaveRequestDto carSaveRequestDto) {
        // company 객체가 있는지 확인하는 부분 isValidCompany로 뺄 것.
        Company company = companyRepository.findByName(carSaveRequestDto.getCompanyName())
                .orElseGet(() -> companyRepository.save(new CompanySaveRequestDto(carSaveRequestDto.getCompanyName()).toEntity()));
        // category 객체가 있는지 확인하는 부분 isValidCategory로 뺄 것.
        Category category = categoryRepository.findByName(carSaveRequestDto.getCategoryName())
                .orElseGet(() -> categoryRepository.save(new CategorySaveRequestDto(carSaveRequestDto.getCategoryName()).toEntity()));

        Car car = carRepository.findByCategoryIdAndName(category.getId(), carSaveRequestDto.getName())
                .orElseGet(() -> carRepository.save(carSaveRequestDto.toEntity(category, company)));
        return car.getId();
    }

    @Transactional
    public List<CarWooriResponseDto> dream(String userIncome, BigDecimal minimum, BigDecimal maximum, FlaskRequestDto data) {
        // 추천 시스템 요청
        CarPythonResponseDto[] list = flaskService.recommendedCars(data).block();
        for (CarPythonResponseDto dto : list) {
            System.out.println(dto);
        }

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
        for (Map<Long, String> a : response) {
            System.out.println(a);
        }
//        List<CarWooriResponseDto> carWooriResponseDtos = new ArrayList<>();
//        return carWooriResponseDtos;
        // 여기까지
//
        List<Long> possible_ids = new ArrayList<>();
        Map<Long, BigDecimal> loanData = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        for (Map<Long, String> object: response) {

            object.forEach((key, value)->
            {
                try {
                    Map<String, Object> m_ap = new HashMap<>();
                    m_ap = mapper.readValue(value, new TypeReference<Map<String, Object>>(){});
                    Map<String, String> m__ap = mapper.convertValue(m_ap.get("dataBody"), Map.class);

//                  API로부터 반환받은 대출 한도 금액(LN_AVL_AM)이 사용자가 입력한 대출 범위에 있는지 확인
                    BigDecimal LN_AVL_AM = new BigDecimal(m__ap.get("LN_AVL_AM"));
                    if (LN_AVL_AM.compareTo(minimum) > 0 && LN_AVL_AM.compareTo(maximum) < 0) {
//                      범위에 들어가면 possible_ids에 추가
                        possible_ids.add(key);
                        loanData.put(key, LN_AVL_AM);
                    }

                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
        }

        List<CarResponseDto> carInIds = findByIdIn(possible_ids);
        List<CarWooriResponseDto> carWooriResponseDtos = new ArrayList<>();

        for (CarResponseDto carResponseDto: carInIds) {
            CompanyResponseDto companyResponseDto = companyService.findByName(carResponseDto.getCompany().getName());
            carWooriResponseDtos.add(new CarWooriResponseDto(carResponseDto, similarityData.get(carResponseDto.getId()), loanData.get(carResponseDto.getId()), companyResponseDto.getLogo()));
        }
        return carWooriResponseDtos;
    }
}
