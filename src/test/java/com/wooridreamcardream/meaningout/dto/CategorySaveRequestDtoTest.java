package com.wooridreamcardream.meaningout.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CategorySaveRequestDtoTest {
    @Test
    public void category_롬복_기능_테스트() {
        //given
        String name = "Hyundai IONIQ 5";
        //when
        CategorySaveRequestDto dto = new CategorySaveRequestDto(name);
        //then
        assertThat(dto.getName()).isEqualTo(name);
    }
}
