package com.wooridreamcardream.meaningout.service;

import com.wooridreamcardream.meaningout.domain.Category;
import com.wooridreamcardream.meaningout.domain.Picture;
import com.wooridreamcardream.meaningout.dto.CategorySaveRequestDto;
import com.wooridreamcardream.meaningout.dto.PictureResponseDto;
import com.wooridreamcardream.meaningout.dto.PictureSaveRequestDto;
import com.wooridreamcardream.meaningout.repository.CategoryRepository;
import com.wooridreamcardream.meaningout.repository.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PictureService {
    private final PictureRepository pictureRepository;
    private final CategoryRepository categoryRepository;

    public List<PictureResponseDto> findByCategoryId(Long categoryId) {
        return pictureRepository.findByCategoryId(categoryId).stream().map(PictureResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public Long save(PictureSaveRequestDto pictureSaveRequestDto) {
        Category category = categoryRepository.findByName(pictureSaveRequestDto.categoryName())
                .orElseGet(() -> categoryRepository.save(new CategorySaveRequestDto(pictureSaveRequestDto.categoryName()).toEntity()));

        Picture picture = pictureRepository.findByCategoryIdAndImageUrl(category.getId(), pictureSaveRequestDto.imageUrl())
                .orElseGet(() -> pictureRepository.save(pictureSaveRequestDto.toEntity(category)));
        return picture.getId();
    }
}
