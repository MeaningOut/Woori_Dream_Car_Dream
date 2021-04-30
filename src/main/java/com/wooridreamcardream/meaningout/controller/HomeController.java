package com.wooridreamcardream.meaningout.controller;

import com.wooridreamcardream.meaningout.dto.CarResponseDto;
import com.wooridreamcardream.meaningout.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final CarService carService;

    @RequestMapping(value="/car")
    public String index(Model model) {
        List<CarResponseDto> responseDto = carService.findByMinPriceAndMaxPrice(new BigDecimal(1655300000), new BigDecimal(1888608800));
        model.addAttribute("title", "title");
        model.addAttribute("cars", responseDto);
        return "car";
    }
}
