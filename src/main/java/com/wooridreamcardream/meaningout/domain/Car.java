package com.wooridreamcardream.meaningout.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor
@Getter
@Entity
@Table(name="cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @Column
    private String name;

    @Column(name="min_price", nullable=false)
    private BigDecimal minPrice;

    @Column(name="max_price", nullable=false)
    private BigDecimal maxPrice;

    @Column(name="company", nullable=false)
    private String company;

    @Column(name="origin", nullable=false)
    private String origin;

    @Column(name="car_type", nullable=false)
    private String carType;

    @Column(name="engine", nullable=false)
    private String engine;

    @Column
    private String charger;

    @Column(name="emission", nullable=false)
    private String emission;

    @Column(name="fuel")
    private String fuel;

    @Column(name="fuel_efficiency")
    private String fuelEfficiency;

    @Column
    private String occupancy;

    @Column(name="drive_type")
    private String driveType;

    @Column(name="gear_shift")
    private String gearShift;

    @Column(name="image_url", nullable = true)
    private String imageUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "car")
    private Collection<BookmarkedCar> bookmarkedCarList = new ArrayList<BookmarkedCar>();

    @Builder
    public Car(Category category, String name,
               BigDecimal minPrice, BigDecimal maxPrice,
               String company, String origin,
               String carType, String engine,
               String charger, String emission,
               String fuel, String fuelEfficiency,
               String occupancy, String driveType,
               String gearShift, String imageUrl) {
        this.category = category;
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
}
