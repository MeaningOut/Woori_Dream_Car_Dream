package com.wooridreamcardream.meaningout.dto.car;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class RequestDataBody {
    private final String DBPE_ANL_ICM_AM;
    private final String CAR_PR;
}
