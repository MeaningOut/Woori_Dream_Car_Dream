package com.wooridreamcardream.domain;

import lombok.Getter;
import javax.persistence.*;

@Getter
@Entity
@Table(name="bookmarked_cars")
public class BookmarkedCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="car_id")
    private Car car;
}
