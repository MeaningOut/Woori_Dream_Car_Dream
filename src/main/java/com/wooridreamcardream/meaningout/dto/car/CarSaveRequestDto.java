package com.wooridreamcardream.meaningout.dto.car;

import com.wooridreamcardream.meaningout.domain.Car;
import com.wooridreamcardream.meaningout.domain.Category;
import com.wooridreamcardream.meaningout.domain.Company;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class CarSaveRequestDto {
    private final String categoryName;
    private final String name;
    private final BigDecimal minPrice;
    private final BigDecimal maxPrice;
    private final String companyName;
    private final String origin;
    private final String carType;
    private final String engine;
    private final String charger;
    private final String emission;
    private final String fuel;
    private final String fuelEfficiency;
    private final String occupancy;
    private final String driveType;
    private final String gearShift;
    private final String imageUrl;

    public Car toEntity(Category category, Company company) {
        return Car.builder()
                .category(category)
                .name(name)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .company(company)
                .origin(origin)
                .carType(carType)
                .engine(engine)
                .charger(charger)
                .emission(emission)
                .fuel(fuel)
                .fuelEfficiency(fuelEfficiency)
                .occupancy(occupancy)
                .driveType(driveType)
                .gearShift(gearShift)
                .imageUrl(imageUrl)
                .build();
    }
}
