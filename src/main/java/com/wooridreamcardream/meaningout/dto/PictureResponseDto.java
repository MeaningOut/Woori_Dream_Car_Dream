package com.wooridreamcardream.meaningout.dto;

import com.wooridreamcardream.meaningout.domain.Picture;
import lombok.Getter;

@Getter
public class PictureResponseDto {
    private final Long id;
    private final String imageUrl;

    public PictureResponseDto(Picture entity) {
        this.id = entity.getId();
        this.imageUrl = entity.getImageUrl();
    }
}
