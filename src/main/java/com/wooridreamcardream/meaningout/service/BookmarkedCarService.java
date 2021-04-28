package com.wooridreamcardream.meaningout.service;

import com.wooridreamcardream.meaningout.domain.BookmarkedCar;
import com.wooridreamcardream.meaningout.dto.BookmarkedCarSaveRequestDto;
import com.wooridreamcardream.meaningout.repository.BookmarkedCarRepository;
import com.wooridreamcardream.meaningout.dto.BookmarkedCarResponseDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
                .orElseGet(() -> bookmarkedCarRepository.save(requestDto.toEntity()));
        return bookmarkedCar.getId();
    }

    @Transactional
    public void delete(Long id) {
        BookmarkedCar bookmarkedCar = bookmarkedCarRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 북마크가 없습니다. id = " + id));
        bookmarkedCarRepository.delete(bookmarkedCar);
    }

    /*
    사용자 id로 bookmarkedcar 조회
     */
    @Transactional
    public List<BookmarkedCarResponseDto> findByUserId(Long user_id) {
        return bookmarkedCarRepository.findByUserId(user_id).stream().map(BookmarkedCarResponseDto::new).collect(Collectors.toList());
    }
}
