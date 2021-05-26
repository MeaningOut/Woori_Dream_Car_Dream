package com.wooridreamcardream.meaningout.dto;

import com.wooridreamcardream.meaningout.domain.Company;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CompanySaveRequestDto {
    private String companyName;

    public CompanySaveRequestDto(String companyName) {
        this.companyName = companyName;
    }

    public Company toEntity() {
        return Company.builder()
                .name(companyName)
                .build();
    }
}
