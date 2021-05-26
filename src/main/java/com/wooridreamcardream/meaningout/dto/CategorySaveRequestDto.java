package com.wooridreamcardream.meaningout.dto;

import com.wooridreamcardream.meaningout.domain.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CategorySaveRequestDto {
    private String name;

    public CategorySaveRequestDto(String name) {
        this.name = name;
    }

    public Category toEntity() {
        return Category.builder()
                .name(name)
                .build();
    }
}
