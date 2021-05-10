package com.wooridreamcardream.meaningout.dto;

import com.wooridreamcardream.meaningout.domain.Category;
import com.wooridreamcardream.meaningout.domain.Picture;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PictureResponseDtoTest {

    @Test
    public void picture_롬복_기능_테스트() {
        //given
        String imageUrl = "imageUrl";
        //when
        PictureResponseDto dto = new PictureResponseDto(new Picture(new Category("hyundai"), "imageUrl"));
        //then
        assertThat(dto.getImageUrl()).isEqualTo(imageUrl);
    }
}
