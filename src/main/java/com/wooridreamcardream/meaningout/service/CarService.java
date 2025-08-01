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

    public List<CarResponseDto> findAll() {
        return carRepository.findAll().stream().map(CarResponseDto::new).collect(Collectors.toList());
    }

    public CarResponseDto findById(Long id) {
        Car entity = carRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 자동차가 없습니다. id = " + id));
        return new CarResponseDto(entity);
    }

    public List<CarResponseDto> findByIdIn(List<Long> ids) {
        return carRepository.findByIdIn(ids).stream().map(CarResponseDto::new).collect(Collectors.toList());
    }

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

    public List<CarWooriResponseDto> recommend(String userIncome, BigDecimal minimum, BigDecimal maximum, FlaskRequestDto data) {
        // 추천 시스템 요청
        CarPythonResponseDto[] list = flaskService.recommendedCars(data);

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

    public Map<Long, BigDecimal> checkedValidCar(List<Map<Long, String>> cars, BigDecimal minimum, BigDecimal maximum) {
        Map<Long, BigDecimal> loanData = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        for (Map<Long, String> object: cars) {

            object.forEach((key, value)->
            {
                Map<String, Object> jsonToMap = new HashMap<>();
                try {
                    // 우리은행 API 응답값(JSON)을 Map<String, Object>로 변환합니다.
                    jsonToMap = mapper.readValue(value, new TypeReference<Map<String, Object>>() {});
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                // dataBody의 value(JSON)을 Map<String, String)로 변환합니다.
                // dataBody 내에 대출 한도 금액 정보가 포함되어 있습니다.
                Map<String, String> dataBody = mapper.convertValue(jsonToMap.get("dataBody"), Map.class);

                // 대출 한도 금액(LN_AVL_AM)이 사용자가 입력한 대출 범위에 있는지 확인
                BigDecimal LN_AVL_AM = new BigDecimal(dataBody.get("LN_AVL_AM"));
                if (LN_AVL_AM.compareTo(minimum) > 0 && LN_AVL_AM.compareTo(maximum) < 0)
                    loanData.put(key, LN_AVL_AM);

            });
        }
        return loanData;
    }

    public List<Long> checkedValidCarId(Map<Long, BigDecimal> cars) {
        return new ArrayList<>(cars.keySet());
    }

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
