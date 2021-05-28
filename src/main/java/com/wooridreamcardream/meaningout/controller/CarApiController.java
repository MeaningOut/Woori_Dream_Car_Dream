package com.wooridreamcardream.meaningout.controller;

import com.wooridreamcardream.meaningout.dto.car.*;
import com.wooridreamcardream.meaningout.service.CarService;
import com.wooridreamcardream.meaningout.dto.car.FlaskRequestDto;
import com.wooridreamcardream.meaningout.service.FlaskService;
import com.wooridreamcardream.meaningout.service.WooriHttpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CarApiController {

    private final CarService carService;
    private final FlaskService flaskService;
    private final WooriHttpService wooriHttpService;

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

