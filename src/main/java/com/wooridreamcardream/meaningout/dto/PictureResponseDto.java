package com.wooridreamcardream.meaningout.dto;

import com.wooridreamcardream.meaningout.domain.Picture;
import lombok.Getter;

@Getter
public class PictureResponseDto {
    private Long id;
    private String imageUrl;

    public PictureResponseDto(Picture entity) {
        this.id = entity.getId();
        this.imageUrl = entity.getImageUrl();
    }
}
