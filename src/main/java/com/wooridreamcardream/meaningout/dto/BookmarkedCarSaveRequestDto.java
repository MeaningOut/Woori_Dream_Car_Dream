package com.wooridreamcardream.meaningout.dto;

import com.wooridreamcardream.meaningout.domain.BookmarkedCar;
import com.wooridreamcardream.meaningout.domain.Car;
import com.wooridreamcardream.meaningout.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookmarkedCarSaveRequestDto {
    private User user;
    private Car car;

    @Builder
    public BookmarkedCarSaveRequestDto(User user, Car car){
        this.user = user;
        this.car = car;
    }

    public BookmarkedCar toEntity() {
        return BookmarkedCar.builder()
                .user(user)
                .car(car)
                .build();
    }
}
