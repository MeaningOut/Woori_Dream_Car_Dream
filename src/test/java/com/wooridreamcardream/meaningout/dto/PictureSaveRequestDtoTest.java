package com.wooridreamcardream.meaningout.dto;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PictureSaveRequestDtoTest {

    @Test
    public void pictureSaveRequestDto_롬복_기능_테스트() {
        //given
        String categoryName = "hyundai";
        String imageUrl = "imageUrl";
        //when
        PictureSaveRequestDto dto = new PictureSaveRequestDto(categoryName, imageUrl);
        //then
        assertThat(dto.getCategoryName()).isEqualTo(categoryName);
        assertThat(dto.getImageUrl()).isEqualTo(imageUrl);
    }
}
