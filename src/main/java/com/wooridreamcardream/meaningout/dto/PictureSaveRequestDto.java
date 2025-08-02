package com.wooridreamcardream.meaningout.dto;

import com.wooridreamcardream.meaningout.domain.Category;
import com.wooridreamcardream.meaningout.domain.Picture;
import lombok.Builder;

public record PictureSaveRequestDto(String categoryName, String imageUrl) {
    @Builder
    public PictureSaveRequestDto {
    }

    public Picture toEntity(Category category) {
        return Picture.builder()
                .category(category)
                .imageUrl(imageUrl)
                .build();
    }
}
