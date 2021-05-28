package com.wooridreamcardream.meaningout.controller;

import com.wooridreamcardream.meaningout.dto.car.CarResponseDto;
import com.wooridreamcardream.meaningout.dto.car.CarWooriResponseDto;
import com.wooridreamcardream.meaningout.dto.PictureResponseDto;
import com.wooridreamcardream.meaningout.service.CarService;
import com.wooridreamcardream.meaningout.dto.car.FlaskRequestDto;
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
    private final PictureService pictureService;

    /**
     *
     * 연 소득, 대출 한도 범위, 차량 인승 등등 사용자의 자동차 취향을 알 수 있는 정보로 사용자 맞춤 자동차 목록을 가지고 와서 화면에 표시한다.
     *
     * @param user_income 사용자 연 소득
     * @param minimum 사용자 대출 한도 범위 (최소)
     * @param maximum 사용자 대출 한도 범위 (최대)
     * @param people 차량 인승
     * @param bodyType 차량 외형
     * @param environmentalProtection 소비신념 (
     * @param fuelEconomy 소비신념 (연비 좋은 차)
     * @param boycottInJapan 소비신념 (일본 불매)
     * @param patrioticCampaign 소비신념 (애국 캠페인)
     * @param vegan 소비신념 (비건 자동차)
     * @param model
     * @return
     */
    @PostMapping("/car/recommend")
    public String recommendCar(@RequestParam("user-income") String user_income, @RequestParam("min") BigDecimal minimum, @RequestParam("max") BigDecimal maximum, @RequestParam("people") int people, @RequestParam("body-type") String bodyType,
                        @RequestParam("environmental-protection") String environmentalProtection,
                        @RequestParam("fuel-economy") String fuelEconomy, @RequestParam("boycott-in-japan") String boycottInJapan,
                        @RequestParam("patriotic-campaign") String patrioticCampaign, @RequestParam("vegan") String vegan, Model model) {
        List<CarWooriResponseDto> dto = carService.recommend(user_income, minimum, maximum
                , FlaskRequestDto.builder()
                        .people(people)
                        .bodyType(bodyType)
                        .environmentalProtection(environmentalProtection)
                        .fuelEconomy(fuelEconomy)
                        .boycottInJapan(boycottInJapan)
                        .patrioticCampaign(patrioticCampaign)
                        .vegan(vegan)
                        .build());

        model.addAttribute("cars", dto);

        return "car";
    }

    /**
     *
     * 자동차의 세부 정보를 볼 수 있다.
     * 자동차 id를 이용해서 자동차의 정보를 가지고 온다.
     *
     * @param id 자동차 id
     * @param model
     * @return
     */
    @GetMapping("/car/{id}/picture")
    public String getCarDetails(@PathVariable Long id, Model model) {
        CarResponseDto dto = carService.findById(id);
        List<PictureResponseDto> dtos = pictureService.findByCategoryId(dto.getCategory().getId());

        model.addAttribute("car", dto);
        model.addAttribute("pictures", dtos);

        return "details";
    }
}
