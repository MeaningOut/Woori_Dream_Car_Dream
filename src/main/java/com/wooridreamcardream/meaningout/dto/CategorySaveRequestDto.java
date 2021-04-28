package com.wooridreamcardream.meaningout.dto;

import com.wooridreamcardream.meaningout.domain.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CategorySaveRequestDto {
    private String categoryName;

    public CategorySaveRequestDto(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category toEntity() {
        return Category.builder()
                .categoryName(categoryName)
                .build();
    }
}
