package com.wooridreamcardream.meaningout.controller;

import com.wooridreamcardream.meaningout.dto.car.CarResponseDto;
import com.wooridreamcardream.meaningout.dto.car.CarWooriResponseDto;
import com.wooridreamcardream.meaningout.dto.PictureResponseDto;
import com.wooridreamcardream.meaningout.service.CarService;
import com.wooridreamcardream.meaningout.dto.car.FlaskRequestDto;
import com.wooridreamcardream.meaningout.service.FlaskService;
import com.wooridreamcardream.meaningout.service.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class CarController {
    private final CarService carService;
    private final FlaskService flaskService;
    private final PictureService pictureService;

    @PostMapping("/car/dream")
    public String dream(@RequestParam("user-income") String user_income, @RequestParam("min") BigDecimal minimum, @RequestParam("max") BigDecimal maximum, @RequestParam("people") int people, @RequestParam("body-type") String bodyType,
                        @RequestParam("environmental-protection") String environmentalProtection,
                        @RequestParam("fuel-economy") String fuelEconomy, @RequestParam("boycott-in-japan") String boycottInJapan,
                        @RequestParam("patriotic-campaign") String patrioticCampaign, @RequestParam("vegan") String vegan, Model model) {
        List<CarWooriResponseDto> dto = carService.dream(user_income, minimum, maximum, new FlaskRequestDto(people, bodyType, environmentalProtection, fuelEconomy, boycottInJapan, patrioticCampaign, vegan));

        model.addAttribute("cars", dto);

        return "car";
    }

    @GetMapping("/car/{id}/picture")
    public String details(@PathVariable Long id, Model model) {
        CarResponseDto dto = carService.findById(id);
        List<PictureResponseDto> pictureResponseDtos = pictureService.findByCategoryId(dto.getCategory().getId());
        model.addAttribute("car", dto);
        model.addAttribute("pictures", pictureResponseDtos);
        return "details";
    }
}
