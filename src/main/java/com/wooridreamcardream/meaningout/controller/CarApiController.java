package com.wooridreamcardream.meaningout.controller;

import com.wooridreamcardream.meaningout.dto.car.CarResponseDto;
import com.wooridreamcardream.meaningout.dto.car.CarSaveRequestDto;
import com.wooridreamcardream.meaningout.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/api/v1/car")
    public Long save(@RequestBody CarSaveRequestDto requestDto) {
        return carService.save(requestDto);
    }
}

