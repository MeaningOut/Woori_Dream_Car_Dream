package com.wooridreamcardream.meaningout.dto;

import com.wooridreamcardream.meaningout.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public record CategorySaveRequestDto(String name) {
    public Category toEntity() {
        return Category.builder()
                .name(name)
                .build();
    }
}
