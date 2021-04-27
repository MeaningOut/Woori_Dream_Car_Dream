package com.wooridreamcardream.meaningout.controller;

import com.wooridreamcardream.meaningout.dto.CarResponseDto;
import com.wooridreamcardream.meaningout.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CarApiController {

    private final CarService carService;

    @GetMapping("/api/v1/car")
    public List<CarResponseDto> findAll() {
        return carService.findAll();
    }

    @GetMapping("/api/v1/car/{id}")
    public CarResponseDto findById(@PathVariable Long id) {
        return carService.findById(id);
    }

    @GetMapping("/api/v1/car/price")
    public List<CarResponseDto> findByMinPriceAndMaxPrice(@RequestParam("minPrice") BigDecimal minPrice, @RequestParam("maxPrice") BigDecimal maxPrice) {
        return carService.findByMinPriceAndMaxPrice(minPrice, maxPrice);
    }

    @GetMapping("/api/v1/car/dream")
    public List<CarResponseDto> dream(@RequestParam("cc") String cc) {
        return carService.dream(cc);
    }
}
