package com.wooridreamcardream.meaningout.dto.car;

import com.wooridreamcardream.meaningout.domain.Category;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CarWooriResponseDto {
    private Long id;
    private Category category;
    private String name;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String company;
    private String carType;
    private String engine;
    private String emission;
    private String charger;
    private String fuel;
    private String fuelEfficiency;
    private String occupancy;
    private String driveType;
    private String gearShift;
    private String imageUrl;
    private String similarity;
    private BigDecimal loanLimit;
    private String logoImageUrl;

    public CarWooriResponseDto(CarResponseDto entity, String similarity, BigDecimal loanLimit, String logoImageUrl) {
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
        this.similarity = similarity;
        this.loanLimit = loanLimit;
        this.logoImageUrl = logoImageUrl;
    }

    @Override
    public String toString() {
        return "CarWooriResponseDto{" +
                "id=" + id +
                ", category=" + category +
                ", name='" + name + '\'' +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", company='" + company + '\'' +
                ", carType='" + carType + '\'' +
                ", engine='" + engine + '\'' +
                ", charger='" + charger + '\'' +
                ", emission='" + emission + '\'' +
                ", fuel='" + fuel + '\'' +
                ", fuelEfficiency='" + fuelEfficiency + '\'' +
                ", occupancy='" + occupancy + '\'' +
                ", driveType='" + driveType + '\'' +
                ", gearShift='" + gearShift + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", similarity='" + similarity + '\'' +
                ", loanLimit=" + loanLimit +
                ", logoImageUrl='" + logoImageUrl + '\'' +
                '}';
    }
}