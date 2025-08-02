package com.wooridreamcardream.meaningout.dto;

import com.wooridreamcardream.meaningout.dto.car.FlaskRequestDto;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class RecommendCarRequestDto {
    private String userIncome;
    private BigDecimal minLoan;
    private BigDecimal maxLoan;
    private FlaskRequestDto preference;
}
