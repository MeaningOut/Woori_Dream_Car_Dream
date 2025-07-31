package com.wooridreamcardream.meaningout.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class HomeControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void index_페이지_로딩() {
        //when
        String body = this.restTemplate.getForObject("/", String.class);

        //then
        assertThat(body).contains("우리은행에서 신차를 사는 새로운 방법");
    }

    @Test
    public void type_페이지_로딩() {
        //when
        String body = this.restTemplate.getForObject("/type", String.class);

        //then
        assertThat(body).contains("연소득과 대출한도는 꼭 선택해주세요!");
    }

    @Test
    public void taste_페이지_로딩() {
        //when
        String body = this.restTemplate.getForObject("/taste", String.class);

        //then
        assertThat(body).contains("어떤 소비신념을 가지셨나요?");
    }
}
