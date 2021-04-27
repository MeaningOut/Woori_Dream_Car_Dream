package com.wooridreamcardream.meaningout.service;

import com.wooridreamcardream.meaningout.domain.BookmarkedCar;
import com.wooridreamcardream.meaningout.dto.BookmarkedCarSaveRequestDto;
import com.wooridreamcardream.meaningout.repository.BookmarkedCarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BookmarkedCarService {
    private final BookmarkedCarRepository bookmarkedCarRepository;

    /*
     * 북마크 기능
     * 저장, 삭제
     */
    @Transactional
    public Long save(BookmarkedCarSaveRequestDto requestDto) {
        BookmarkedCar bookmarkedCar = bookmarkedCarRepository.findByUserIdAndCarId(requestDto.getUser(), requestDto.getCar())
                .orElse(bookmarkedCarRepository.save(requestDto.toEntity()));
        return bookmarkedCar.getId();
    }

    @Transactional
    public Long delete(Long id) {
        bookmarkedCarRepository.deleteById(id);
        return id;
    }
}
