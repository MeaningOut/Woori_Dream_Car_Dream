package com.wooridreamcardream.meaningout.controller;

import com.wooridreamcardream.meaningout.dto.PictureSaveRequestDto;
import com.wooridreamcardream.meaningout.service.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PictureApiController {
    private final PictureService pictureService;

    @PostMapping("/api/v1/picture")
    public ResponseEntity<Long> save(@RequestBody PictureSaveRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pictureService.save(requestDto));
    }
}
