package com.wooridreamcardream.meaningout.dto.car;

import com.wooridreamcardream.meaningout.domain.Category;
import com.wooridreamcardream.meaningout.domain.Company;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
public class CarWooriResponseDto {
    private final Long id;
    private final Category category;
    private final String name;
    private final BigDecimal minPrice;
    private final BigDecimal maxPrice;
    private final Company company;
    private final String carType;
    private final String engine;
    private final String emission;
    private final String charger;
    private final String fuel;
    private final String fuelEfficiency;
    private final String occupancy;
    private final String driveType;
    private final String gearShift;
    private final String imageUrl;
    private final BigDecimal loanLimit;

    public CarWooriResponseDto(CarResponseDto entity, BigDecimal loanLimit) {
        this.id = entity.getId();
        this.category = entity.getCategory();
        this.name = entity.getName();
        this.minPrice = entity.getMinPrice();
        this.maxPrice = entity.getMaxPrice();
        this.company = entity.getCompany();
        this.carType = entity.getCarType();
        this.engine = entity.getEngine();
        this.emission = entity.getEmission();
        this.charger = entity.getCharger();
        this.fuel = entity.getFuel();
        this.fuelEfficiency = entity.getFuelEfficiency();
        this.occupancy = entity.getOccupancy();
        this.driveType = entity.getDriveType();
        this.gearShift = entity.getGearShift();
        this.imageUrl = entity.getImageUrl();
        this.loanLimit = loanLimit;
    }
}
