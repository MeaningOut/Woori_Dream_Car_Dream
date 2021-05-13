package com.wooridreamcardream.meaningout.service;

import com.wooridreamcardream.meaningout.domain.Category;
import com.wooridreamcardream.meaningout.domain.Picture;
import com.wooridreamcardream.meaningout.dto.PictureSaveRequestDto;
import com.wooridreamcardream.meaningout.repository.PictureRepository;
import org.junit.Test;
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

    @Test
    public void 사진_저장() {
        //given
        PictureSaveRequestDto dto = createPictureRequest();
        Category category = new Category(1L, "hyundai");
        Picture picture = new Picture(1L, category, "imageUrl");

//        Long fakePictureId = 1L;
//        ReflectionTestUtils.setField(picture, "id", fakePictureId);

        // mocking
        given(pictureRepository.save(any())).willReturn(picture);
//        given(pictureRepository.findById(fakePictureId)).willReturn(Optional.ofNullable(picture));

        //when
//        Long newPictureId = pictureService.save(dto);

        //then
//        Picture findPicture = pictureRepository.findById(newPictureId).get();

//        assertEquals(picture.getId(), findPicture.getId());
//        assertEquals(picture.getCategory().getCategoryName(), findPicture.getCategory().getCategoryName());
//        assertEquals(picture.getImageUrl(), findPicture.getImageUrl());
    }

    private Picture createPictureEntity(PictureSaveRequestDto dto) {
        return new Picture(1L, new Category(dto.getCategoryName()), dto.getImageUrl());
//        return Picture.builder()
//                .category(new Category(dto.getCategoryName()))
//                .imageUrl(dto.getImageUrl())
//                .build();
    }

    private PictureSaveRequestDto createPictureRequest() {
        PictureSaveRequestDto dto = PictureSaveRequestDto.builder()
                .categoryName("hyundai")
                .imageUrl("imageUrl")
                .build();
        return dto;
    }
}
