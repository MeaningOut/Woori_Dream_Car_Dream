package com.wooridreamcardream.meaningout.dto.car;

import lombok.Builder;

@Builder
public record CarWooriRequestDto(Long carId, RequestDataBody dataBody) {
}
