package com.wooridreamcardream.meaningout.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.beans.ConstructorProperties;
import java.math.BigDecimal;

@Getter
public class CarPythonResponseDto {
    private String id;
    private String avg_price;
    private String similarity;

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
