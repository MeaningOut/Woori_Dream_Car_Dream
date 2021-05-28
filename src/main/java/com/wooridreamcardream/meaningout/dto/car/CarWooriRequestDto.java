package com.wooridreamcardream.meaningout.dto.car;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CarWooriRequestDto {
    private Long carId;
    private RequestDataBody dataBody;

    @Builder
    public CarWooriRequestDto(Long carId, RequestDataBody dataBody) {
        this.carId = carId;
        this.dataBody = dataBody;
    }

    @Override
    public String toString() {
        return "CarWooriRequestDto{" +
                "carId=" + String.valueOf(carId) +
                ", dataBody=" + dataBody +
                '}';
    }
}
