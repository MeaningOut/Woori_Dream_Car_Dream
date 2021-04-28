package com.wooridreamcardream.meaningout.controller;

import com.wooridreamcardream.meaningout.dto.BookmarkedCarResponseDto;
import com.wooridreamcardream.meaningout.dto.BookmarkedCarSaveRequestDto;
import com.wooridreamcardream.meaningout.service.BookmarkedCarService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BookmarkedCarController {
    private final BookmarkedCarService bookmarkedCarService;

    @GetMapping("/user/{user_id}/bookmarkedcar")
    public String userBookmarkedCarList(@PathVariable Long user_id, Model model) {
        List<BookmarkedCarResponseDto> dto = bookmarkedCarService.findByUserId(user_id);
        model.addAttribute("bookmarkedCarLists", dto);

        return "bookmarkedCarList";
    }

//    @PostMapping("/user/{user_id}/car/{car_id}/bookmarkedcar")
//    public String saveBookmarkedCar(@PathVariable Long user_id, @PathVariable Long car_id, @RequestBody BookmarkedCarSaveRequestDto requestDto) {
//
//    }

    @DeleteMapping("/bookmarkedcar/{id}")
    public Long delete(@PathVariable Long id) {
        bookmarkedCarService.delete(id);
        return id;
    }
}
