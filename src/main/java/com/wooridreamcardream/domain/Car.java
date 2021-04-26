package com.wooridreamcardream.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

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
    private Integer minPrice;

    @Column(name="max_price", nullable=false)
    private Integer maxPrice;

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

    @Column(name="image_url", nullable=false)
    private String imageUrl;

    @OneToMany(mappedBy = "car")
    private Collection<BookmarkedCar> bookmarkedCarList = new ArrayList<BookmarkedCar>();
}
