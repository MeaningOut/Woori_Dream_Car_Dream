package com.wooridreamcardream.meaningout.dto.car;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CarPythonResponseDto {
    private String id;
    private String avg_price; // 차량 가격
    private String similarity; // 사용자 소비 신념 유사도

    public CarPythonResponseDto(@JsonProperty("id") String id, @JsonProperty("avg_price") String avgPrice, @JsonProperty("similarity") String similarity) {
        this.id = id;
        this.avg_price = avgPrice;
        this.similarity = similarity;
    }

    @Override
    public String toString() {
        return "CarPythonResponseDto{" +
                "id=" + id +
                ", avgPrice=" + avg_price +
                ", similarity=" + similarity +
                '}';
    }
}
