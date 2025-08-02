package com.wooridreamcardream.meaningout.controller;

import com.wooridreamcardream.meaningout.dto.RecommendCarRequestDto;
import com.wooridreamcardream.meaningout.dto.car.CarResponseDto;
import com.wooridreamcardream.meaningout.dto.car.CarWooriResponseDto;
import com.wooridreamcardream.meaningout.dto.PictureResponseDto;
import com.wooridreamcardream.meaningout.service.CarService;
import com.wooridreamcardream.meaningout.service.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/car")
@Controller
public class CarController {
    private final CarService carService;
    private final PictureService pictureService;

    @PostMapping("/recommend")
    public String recommendCar(@ModelAttribute RecommendCarRequestDto requestDto, Model model) {
        List<CarWooriResponseDto> recommendedCars = carService.recommend(
                requestDto.getUserIncome(),
                requestDto.getMinLoan(),
                requestDto.getMaxLoan(),
                requestDto.getPreference()
        );

        model.addAttribute("cars", recommendedCars);
        return "car";
    }

    @GetMapping("/{id}/picture")
    public String getCarDetails(@PathVariable Long id, Model model) {
        CarResponseDto dto = carService.findById(id);
        List<PictureResponseDto> pictures = pictureService.findByCategoryId(dto.getCategory().getId());

        model.addAttribute("car", dto);
        model.addAttribute("pictures", pictures);

        return "details";
    }
}
