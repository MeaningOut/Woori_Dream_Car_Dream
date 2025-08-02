package com.wooridreamcardream.meaningout.dto.car;

import com.wooridreamcardream.meaningout.domain.Car;
import com.wooridreamcardream.meaningout.domain.Category;
import com.wooridreamcardream.meaningout.domain.Company;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CarResponseDto {
    private final Long id;
    private final Category category;
    private final String name;
    private final BigDecimal minPrice;
    private final BigDecimal maxPrice;
    private final Company company;
    private final String charger;
    private final String carType;
    private final String engine;
    private final String emission;
    private final String fuel;
    private final String fuelEfficiency;
    private final String occupancy;
    private final String driveType;
    private final String gearShift;
    private final String imageUrl;

    public CarResponseDto(Car entity) {
        this.id = entity.getId();
        this.category = entity.getCategory();
        this.name = entity.getName();
        this.minPrice = entity.getMinPrice();
        this.maxPrice = entity.getMaxPrice();
        this.company = entity.getCompany();
        this.carType = entity.getCarType();
        this.engine = entity.getEngine();
        this.charger = entity.getCharger();
        this.emission = entity.getEmission();
        this.fuel = entity.getFuel();
        this.fuelEfficiency = entity.getFuelEfficiency();
        this.occupancy = entity.getOccupancy();
        this.driveType = entity.getDriveType();
        this.gearShift = entity.getGearShift();
        this.imageUrl = entity.getImageUrl();
    }
}
