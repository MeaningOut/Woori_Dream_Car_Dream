package com.wooridreamcardream.meaningout.controller;

import com.wooridreamcardream.meaningout.dto.car.*;
import com.wooridreamcardream.meaningout.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/car")
@RestController
public class CarApiController {
    private final CarService carService;

    @GetMapping
    public ResponseEntity<List<CarResponseDto>> findAll() {
        return ResponseEntity.ok(carService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponseDto> findById(@PathVariable Long id) {
        CarResponseDto car = carService.findById(id);
        return ResponseEntity.ok(car);
    }

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody CarSaveRequestDto requestDto) {
        Long savedId = carService.save(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedId);
    }
}