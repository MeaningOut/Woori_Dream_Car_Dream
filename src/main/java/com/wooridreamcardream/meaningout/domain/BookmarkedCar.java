package com.wooridreamcardream.meaningout.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
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

    @Builder
    public BookmarkedCar(User user, Car car) {
        this.user = user;
        this.car = car;
    }
}
