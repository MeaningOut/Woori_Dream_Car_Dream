package com.wooridreamcardream.meaningout.dto;

import com.wooridreamcardream.meaningout.domain.BookmarkedCar;
import com.wooridreamcardream.meaningout.domain.User;
import com.wooridreamcardream.meaningout.domain.Car;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BookmarkedCarResponseDto {
    private Long id;
    private User user;
    private Car car;

    public BookmarkedCarResponseDto(BookmarkedCar entity) {
        this.id = entity.getId();
        this.user = entity.getUser();
        this.car = entity.getCar();
    }
}
