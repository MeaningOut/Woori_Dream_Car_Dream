package com.wooridreamcardream.meaningout.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserTasteUpdateRequestDto {
    private String cc;

    @Builder
    public UserTasteUpdateRequestDto(String cc) {
        this.cc = cc;
    }
}
