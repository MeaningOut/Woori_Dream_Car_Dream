package com.wooridreamcardream.meaningout.dto;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RequestDataBody {
    private String DBPE_ANL_ICM_AM;
    private String CAR_PR;

    @Builder
    public RequestDataBody(String DBPE_ANL_ICM_AM, String CAR_PR) {
        this.DBPE_ANL_ICM_AM = DBPE_ANL_ICM_AM;
        this.CAR_PR = CAR_PR;
    }

    @Override
    public String toString() {
        return "RequestDataBody{" +
                "DBPE_ANL_ICM_AM='" + DBPE_ANL_ICM_AM + '\'' +
                ", CAR_PR='" + CAR_PR + '\'' +
                '}';
    }
}
