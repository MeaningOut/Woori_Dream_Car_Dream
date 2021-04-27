package com.wooridreamcardream.meaningout.dto;

import lombok.Getter;
import com.wooridreamcardream.meaningout.domain.Category;

@Getter
public class CategoryResponseDto {
    private Long id;
    private String categoryName;

    public CategoryResponseDto(Category entity) {
        this.id = entity.getId();
        this.categoryName = entity.getCategoryName();
    }
}
