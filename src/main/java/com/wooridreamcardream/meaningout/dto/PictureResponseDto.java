package com.wooridreamcardream.meaningout.dto;

import com.wooridreamcardream.meaningout.domain.Picture;

public class PictureResponseDto {
    private Long id;
    private String imageUrl;

    public PictureResponseDto(Picture entity) {
        this.id = entity.getId();
        this.imageUrl = entity.getImageUrl();
    }
}
