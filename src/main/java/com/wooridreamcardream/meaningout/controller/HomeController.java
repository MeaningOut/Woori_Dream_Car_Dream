package com.wooridreamcardream.meaningout.controller;

import com.wooridreamcardream.meaningout.dto.CarResponseDto;
import com.wooridreamcardream.meaningout.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final CarService carService;

    @GetMapping ("/")
    public String index(Model model) {
        return "home";
    }

    @GetMapping("/sub")
    public String sub(@RequestParam("user-income") String user_income,
                      @RequestParam("min") BigDecimal minimum,
                      @RequestParam("max") BigDecimal maximum,
                      @RequestParam("people") int people,
                      @RequestParam("body-type") String body_type, Model model) {
        model.addAttribute("userIncome", user_income);
        model.addAttribute("minimum", minimum);
        model.addAttribute("maximum", maximum);
        model.addAttribute("people", people);
        model.addAttribute("bodyType", body_type);
        return "sub";
    }
}
