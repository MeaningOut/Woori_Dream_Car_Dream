package com.wooridreamcardream.meaningout.service;

import com.wooridreamcardream.meaningout.dto.car.CarPythonResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.*;

@Service
public class FlaskService {
    private final WebClient webClient;

    public FlaskService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://127.0.0.1:5000").build();
    }

    /**
     *
     * @param data 플라스크 추천 서버에 보내야 하는 데이터 (인승, 차량 타입, 소비 신념 5가지 동의 항목)
     * @return
     */
    public Mono<CarPythonResponseDto[]> recommendedCars(FlaskData data) {
        return webClient.post()
                .uri("/refined-cars")
                .body(fromFormData("people", String.valueOf(data.getPeople()))
                        .with("body-type", data.getBodyType())
                        .with("environmental-protection", data.getEnvironmentalProtection())
                        .with("fuel-economy",data.getFuelEconomy())
                        .with("boycott-in-japan", data.getBoycottInJapan())
                        .with("patriotic-campaign", data.getPatrioticCampaign())
                        .with("vegan", data.getVegan()))
                .retrieve()
                .bodyToMono(CarPythonResponseDto[].class);
    }
}
