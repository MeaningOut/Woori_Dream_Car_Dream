package com.wooridreamcardream.meaningout.controller;

import com.wooridreamcardream.meaningout.dto.car.*;
import com.wooridreamcardream.meaningout.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CarApiController {

    private final CarService carService;

    /**
     * 데이터베이스에 저장된 모든 자동차 데이터를 가져옵니다.
     * @return
     */
    @GetMapping("/api/v1/car")
    public List<CarResponseDto> findAll() {
        return carService.findAll();
    }

    /**
     * 자동차 id에 해당하는 자동차 데이터를 가져옵니다.
     * @param id 자동차 id
     * @return
     */
    @GetMapping("/api/v1/car/{id}")
    public CarResponseDto findById(@PathVariable Long id) {
        return carService.findById(id);
    }

    /**
     * 자동차 데이터를 데이터베이스에 저장합니다.
     * @param requestDto 저장하고자 하는 자동차 데이터
     * @return
     */
    @PostMapping("/api/v1/car")
    public Long save(@RequestBody CarSaveRequestDto requestDto) {
        return carService.save(requestDto);
    }
}

