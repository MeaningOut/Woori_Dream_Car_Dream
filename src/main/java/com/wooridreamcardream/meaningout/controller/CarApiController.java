package com.wooridreamcardream.meaningout.controller;

import com.wooridreamcardream.meaningout.dto.CarResponseDto;
import com.wooridreamcardream.meaningout.dto.CarSaveRequestDto;
import com.wooridreamcardream.meaningout.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/api/v1/car")
    public Long save(@RequestBody CarSaveRequestDto requestDto) {
        return carService.save(requestDto);
    }

    // http://localhost:5000/refined-cars?people=5&body-type=소형&environmental-protection=Y&fuel-economy=Y&boycott-in-japan=Y&patriotic-campaign=Y&vegan=N
    @GetMapping("/api/v1/car/dream")
    public void dream(@RequestParam("user-income") BigDecimal user_income, @RequestParam("people") int people, @RequestParam("body-type") String body_type,
                                      @RequestParam("environmental-protection") String environmental_protection,
                                      @RequestParam("fuel-economy") String fuel_economy, @RequestParam("boycott-in-japan") String boycott_in_japan,
                                      @RequestParam("patriotic-campaign") String patriotic_campaign, @RequestParam("vegan") String vegan) {
        carService.dream(user_income, people, body_type, environmental_protection, fuel_economy, boycott_in_japan, patriotic_campaign, vegan);
    }
}
