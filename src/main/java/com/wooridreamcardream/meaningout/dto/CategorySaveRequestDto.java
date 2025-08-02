package com.wooridreamcardream.meaningout.dto;

import com.wooridreamcardream.meaningout.domain.Category;

public record CategorySaveRequestDto(String name) {
    public Category toEntity() {
        return Category.builder()
                .name(name)
                .build();
    }
}
