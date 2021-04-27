package com.wooridreamcardream.meaningout.service;

import com.wooridreamcardream.meaningout.dto.PictureResponseDto;
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

    @Transactional
    public List<PictureResponseDto> findByCategoryId(Long categoryId) {
        return pictureRepository.findByCategoryId(categoryId).stream().map(PictureResponseDto::new).collect(Collectors.toList());
    }
}
