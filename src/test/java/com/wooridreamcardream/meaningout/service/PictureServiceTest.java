package com.wooridreamcardream.meaningout.service;

import com.wooridreamcardream.meaningout.domain.Category;
import com.wooridreamcardream.meaningout.domain.Picture;
import com.wooridreamcardream.meaningout.dto.PictureSaveRequestDto;
import com.wooridreamcardream.meaningout.repository.CategoryRepository;
import com.wooridreamcardream.meaningout.repository.PictureRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(SpringExtension.class)
public class PictureServiceTest {
    @InjectMocks
    private PictureService pictureService;

    @Mock
    private PictureRepository pictureRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    public void 사진_저장() {
        //given
        PictureSaveRequestDto dto = createPictureRequest();
        Picture picture = createPictureEntity(dto);

        Long fakePictureId = 1L;
        ReflectionTestUtils.setField(picture, "id", fakePictureId);

        // mocking
        given(pictureRepository.save(any())).willReturn(picture);
        given(pictureRepository.findById(fakePictureId)).willReturn(Optional.ofNullable(picture));

        //when
        Long newPictureId = pictureService.save(dto);

        //then
        Picture findPicture = pictureRepository.findById(newPictureId).get();

        assertEquals(picture.getId(), findPicture.getId());
        assertEquals(picture.getCategory().getName(), findPicture.getCategory().getName());
        assertEquals(picture.getImageUrl(), findPicture.getImageUrl());
    }

    private Picture createPictureEntity(PictureSaveRequestDto dto) {
        Category category = new Category(1L, "Hyundai IONIQ 5");
        return new Picture(1L, category, dto.getImageUrl());
//        return Picture.builder()
//                .category(new Category(dto.getName()))
//                .imageUrl(dto.getImageUrl())
//                .build();
    }

//    @Asy
    private PictureSaveRequestDto createPictureRequest() {
        PictureSaveRequestDto dto = PictureSaveRequestDto.builder()
                .categoryName("Hyundai IONIQ 5")
                .imageUrl("imageUrl")
                .build();
        return dto;
    }
}
