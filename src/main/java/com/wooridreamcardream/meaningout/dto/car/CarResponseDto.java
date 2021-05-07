package com.wooridreamcardream.meaningout.dto.car;

import com.wooridreamcardream.meaningout.domain.Car;
import com.wooridreamcardream.meaningout.domain.Category;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CarResponseDto {
    private Long id;
    private Category category;
    private String name;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String company;
    private String charger;
    private String carType;
    private String engine;
    private String emission;
    private String fuel;
    private String fuelEfficiency;
    private String occupancy;
    private String driveType;
    private String gearShift;
    private String imageUrl;

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
