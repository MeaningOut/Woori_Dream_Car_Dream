package com.wooridreamcardream.meaningout.service;

import com.wooridreamcardream.meaningout.domain.Car;
import com.wooridreamcardream.meaningout.dto.CarResponseDto;
import com.wooridreamcardream.meaningout.repository.CarRepository;
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
