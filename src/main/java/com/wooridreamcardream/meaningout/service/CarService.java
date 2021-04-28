package com.wooridreamcardream.meaningout.service;

import com.wooridreamcardream.meaningout.domain.Car;
import com.wooridreamcardream.meaningout.domain.Category;

import com.wooridreamcardream.meaningout.dto.CarResponseDto;
import com.wooridreamcardream.meaningout.dto.CarSaveRequestDto;
import com.wooridreamcardream.meaningout.dto.CategorySaveRequestDto;
import com.wooridreamcardream.meaningout.repository.CarRepository;
import com.wooridreamcardream.meaningout.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CarService {
    private final CarRepository carRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public List<CarResponseDto> findByMinPriceAndMaxPrice(BigDecimal minPrice, BigDecimal maxPrice) {
        return carRepository.findByMinPriceGreaterThanEqualAndMaxPriceLessThanEqual(minPrice, maxPrice).stream().map(CarResponseDto::new).collect(Collectors.toList());
    }
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
    public Long save(CarSaveRequestDto carSaveRequestDto) {
        Category category = categoryRepository.findByCategoryName(carSaveRequestDto.getCategoryName())
                .orElseGet(() -> categoryRepository.save(new CategorySaveRequestDto(carSaveRequestDto.getCategoryName()).toEntity()));

        Car car = carRepository.findByCategoryIdAndName(category.getId(), carSaveRequestDto.getName())
                .orElseGet(() -> carRepository.save(carSaveRequestDto.toEntity(category)));
        return car.getId();
    }
//    @Transactional
//    public List<CarResponseDto> dream(String cc) {
//
//        List<Car> beforeMeaningOutCarList = carRepository.findByCC(cc);
//
//        // 추천 시스템
//
//        // 한도 범위 검사
//
//        //
//    }
}
