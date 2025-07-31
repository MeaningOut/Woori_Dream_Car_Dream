package com.wooridreamcardream.meaningout.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PictureSaveRequestDtoTest {

    @Test
    public void pictureSaveRequestDto_롬복_기능_테스트() {
        //given
        String name = "Hyundai IONIQ 5";
        String imageUrl = "imageUrl";
        //when
        PictureSaveRequestDto dto = new PictureSaveRequestDto(name, imageUrl);
        //then
        assertThat(dto.getCategoryName()).isEqualTo(name);
        assertThat(dto.getImageUrl()).isEqualTo(imageUrl);
    }
}
