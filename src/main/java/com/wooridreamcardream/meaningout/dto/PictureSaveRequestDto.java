package com.wooridreamcardream.meaningout.dto;

import com.wooridreamcardream.meaningout.domain.Category;
import com.wooridreamcardream.meaningout.domain.Picture;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PictureSaveRequestDto {
    private String categoryName;
    private String imageUrl;

    @Builder
    public PictureSaveRequestDto(String categoryName, String imageUrl) {
        this.categoryName = categoryName;
        this.imageUrl = imageUrl;
    }

    public Picture toEntity(Category category) {
        return Picture.builder()
                .category(category)
                .imageUrl(imageUrl)
                .build();
    }
}
