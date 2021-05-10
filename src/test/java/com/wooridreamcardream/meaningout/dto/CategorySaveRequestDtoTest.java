package com.wooridreamcardream.meaningout.dto;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CategorySaveRequestDtoTest {
    @Test
    public void category_롬복_기능_테스트() {
        //given
        String categoryName = "hyundai";
        //when
        CategorySaveRequestDto dto = new CategorySaveRequestDto(categoryName);
        //then
        assertThat(dto.getCategoryName()).isEqualTo(categoryName);
    }
}
