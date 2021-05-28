package com.wooridreamcardream.meaningout.dto.car;

import lombok.Getter;

@Getter
public class FlaskRequestDto {
    private Integer people;
    private String bodyType;
    private String environmentalProtection;
    private String fuelEconomy;
    private String boycottInJapan;
    private String patrioticCampaign;
    private String vegan;

    public FlaskRequestDto(Integer people, String bodyType, String environmentalProtection, String fuelEconomy, String boycottInJapan, String patrioticCampaign, String vegan) {
        this.people = people;
        this.bodyType = bodyType;
        this.environmentalProtection = environmentalProtection;
        this.fuelEconomy = fuelEconomy;
        this.boycottInJapan = boycottInJapan;
        this.patrioticCampaign = patrioticCampaign;
        this.vegan = vegan;
    }
}
