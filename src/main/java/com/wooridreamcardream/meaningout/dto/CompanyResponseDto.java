package com.wooridreamcardream.meaningout.dto;

import com.wooridreamcardream.meaningout.domain.Company;
import lombok.Getter;

@Getter
public class CompanyResponseDto {
    private Long id;
    private String name;
    private String logo;

    public CompanyResponseDto(Company company) {
        this.id = company.getId();
        this.name = company.getName();
        this.logo = company.getLogo();
    }
}
