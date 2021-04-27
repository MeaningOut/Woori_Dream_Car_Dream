package com.wooridreamcardream.meaningout.service;

import com.wooridreamcardream.meaningout.repository.CategoryRepository;
import com.wooridreamcardream.meaningout.dto.CategoryResponseDto;
import com.wooridreamcardream.meaningout.domain.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    /*
    * 자동차의 big title이 궁금할 때 자동차의 categoryId로 검색.
    * */
    @Transactional
    public CategoryResponseDto findById(Long id) {
        Category entity = categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 범주는 없습니다."));
        return new CategoryResponseDto(entity);
    }
}
