package com.wooridreamcardream.meaningout.service;

import com.wooridreamcardream.meaningout.dto.car.CarPythonResponseDto;
import com.wooridreamcardream.meaningout.dto.car.FlaskRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.springframework.web.reactive.function.BodyInserters.*;

@Service
public class FlaskService {
    private static final String URL = "/refined-cars";
    private static final String FLASK_URL = "http://127.0.0.1:5000";

    private final WebClient webClient;

    public FlaskService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(FLASK_URL).build();
    }

    public CarPythonResponseDto[] recommendedCars(FlaskRequestDto dto) {
        return webClient.post()
                .uri(URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(fromFormData("people", String.valueOf(dto.getPeople()))
                        .with("body-type", dto.getBodyType())
                        .with("environmental-protection", dto.getEnvironmentalProtection())
                        .with("fuel-economy",dto.getFuelEconomy())
                        .with("boycott-in-japan", dto.getBoycottInJapan())
                        .with("patriotic-campaign", dto.getPatrioticCampaign())
                        .with("vegan", dto.getVegan()))
                .retrieve()
                .onStatus(
                        status -> status.isError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(body ->
                                        Mono.error(new RuntimeException("Flask error: " + body))
                                )
                )
                .bodyToMono(CarPythonResponseDto[].class)
                .timeout(Duration.ofSeconds(3))
                .block();
    }
}
