package com.wooridreamcardream.meaningout.service;

import com.wooridreamcardream.meaningout.domain.UserTaste;
import com.wooridreamcardream.meaningout.dto.UserTasteUpdateRequestDto;
import com.wooridreamcardream.meaningout.repository.UserTasteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserTasteService {
    private final UserTasteRepository userTasteRepository;

    /*
    사용자 취향 저장 및 변경
     */
    @Transactional
    public Long update(Long id, UserTasteUpdateRequestDto requestDto) {
        UserTaste userTaste = userTasteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("사용자 취향이 존재하지 않습니다."));
        userTaste.update(requestDto.getCc());
        return id;
    }
}
