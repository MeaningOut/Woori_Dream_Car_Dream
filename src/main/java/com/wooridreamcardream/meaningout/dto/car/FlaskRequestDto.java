package com.wooridreamcardream.meaningout.dto.car;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class FlaskRequestDto {
    private final Integer people;
    private final String bodyType;
    private final String environmentalProtection;
    private final String fuelEconomy;
    private final String boycottInJapan;
    private final String patrioticCampaign;
    private final String vegan;
}
