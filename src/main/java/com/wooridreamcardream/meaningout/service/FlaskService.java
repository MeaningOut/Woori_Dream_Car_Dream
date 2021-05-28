package com.wooridreamcardream.meaningout.service;

import com.wooridreamcardream.meaningout.dto.car.CarPythonResponseDto;
import com.wooridreamcardream.meaningout.dto.car.FlaskRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.*;

@Service
public class FlaskService {
    static final String URL = "/refined-cars";
    static final String flaskURL = "http://127.0.0.1:5000";

    private final WebClient webClient;

    public FlaskService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(flaskURL).build();
    }

    /**
     * 플라스크 추천 서버에 사용자 취향 정보 데이터를 담아서 추천 자동차 요청을 보낸다.
     *
     * @param dto 플라스크 추천 서버에 보내야 하는 데이터 (인승, 차량 타입, 소비 신념 5가지 동의 항목)
     * @return
     */
    public Mono<CarPythonResponseDto[]> recommendedCars(FlaskRequestDto dto) {
        return webClient.post()
                .uri(URL)
                .body(fromFormData("people", String.valueOf(dto.getPeople()))
                        .with("body-type", dto.getBodyType())
                        .with("environmental-protection", dto.getEnvironmentalProtection())
                        .with("fuel-economy",dto.getFuelEconomy())
                        .with("boycott-in-japan", dto.getBoycottInJapan())
                        .with("patriotic-campaign", dto.getPatrioticCampaign())
                        .with("vegan", dto.getVegan()))
                .retrieve()
                .bodyToMono(CarPythonResponseDto[].class);
    }
}
