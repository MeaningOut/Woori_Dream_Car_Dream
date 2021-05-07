package com.wooridreamcardream.meaningout.dto.car;

import com.wooridreamcardream.meaningout.domain.Car;
import com.wooridreamcardream.meaningout.domain.Category;
import com.wooridreamcardream.meaningout.repository.CategoryRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
public class CarSaveRequestDto {
    private String categoryName;
    private String name;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String company;
    private String origin;
    private String carType;
    private String engine;
    private String charger;
    private String emission;
    private String fuel;
    private String fuelEfficiency;
    private String occupancy;
    private String driveType;
    private String gearShift;
    private String imageUrl;

    @Builder
    public CarSaveRequestDto(String categoryName, String name,
                             BigDecimal minPrice, BigDecimal maxPrice,
                             String company, String origin,
                             String carType, String engine,
                             String charger, String emission,
                             String fuel, String fuelEfficiency,
                             String occupancy, String driveType,
                             String gearShift, String imageUrl) {
        this.categoryName = categoryName;
        this.name = name;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.company = company;
        this.origin = origin;
        this.carType = carType;
        this.engine = engine;
        this.charger = charger;
        this.emission = emission;
        this.fuel = fuel;
        this.fuelEfficiency = fuelEfficiency;
        this.occupancy = occupancy;
        this.driveType = driveType;
        this.gearShift = gearShift;
        this.imageUrl = imageUrl;
    }

    public Car toEntity(Category category) {
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
