package com.wooridreamcardream.meaningout.controller;

import com.wooridreamcardream.meaningout.dto.PictureSaveRequestDto;
import com.wooridreamcardream.meaningout.service.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PictureApiController {
    private final PictureService pictureService;

    @PostMapping("/api/v1/picture")
    public Long save(@RequestBody PictureSaveRequestDto requestDto) {
        return pictureService.save(requestDto);
    }
}
