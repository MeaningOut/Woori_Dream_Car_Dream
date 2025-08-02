package com.wooridreamcardream.meaningout.dto;

import com.wooridreamcardream.meaningout.domain.Company;

public record CompanySaveRequestDto(String companyName) {
    public Company toEntity() {
        return Company.builder()
                .name(companyName)
                .build();
    }
}
