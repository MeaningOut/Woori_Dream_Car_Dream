package com.wooridreamcardream.meaningout.dto.car;

import com.wooridreamcardream.meaningout.domain.Category;
import com.wooridreamcardream.meaningout.domain.Company;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CarWooriResponseDto {
    private Long id;
    private Category category;
    private String name;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Company company;
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
    private BigDecimal loanLimit;

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
                ", loanLimit='" + loanLimit + '\'' +
                '}';
    }
}
