package com.wooridreamcardream.meaningout.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wooridreamcardream.meaningout.dto.car.CarWooriRequestDto;
import com.wooridreamcardream.meaningout.dto.woori.WooriRequest;

import com.wooridreamcardream.meaningout.exception.WooriJsonSerializationException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import lombok.extern.slf4j.Slf4j;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class WooriHttpService {
    static final String HEADER_VALUE = "l7xxDPvgxEKY9hmvslvoN4Hj3IaJ3REkmqXD";
    static final String APP_KEY = "appkey";
    static final String URL = "/oai/wb/v1/newcar/getNewCarLoanAm";
    static final String BASE_URL = "http://127.0.0.1:5000";
    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public WooriHttpService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(BASE_URL).build();
    }

    public Mono<String> readyToWooriApi(CarWooriRequestDto dto) {
        String requestBody = makeBody(dto);

        return webClient.post()
                .uri(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers -> headers.add(APP_KEY, HEADER_VALUE))
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(String.class);
    }

    public List<Map<Long, String>> request(List<CarWooriRequestDto> dtoList) {
        List<Map<Long, String>> response = new ArrayList<>();

        for(CarWooriRequestDto dto: dtoList) {
            try {
                String res = readyToWooriApi(dto).block(Duration.ofSeconds(5));
                Map<Long, String> resultMap = new HashMap<>();
                resultMap.put(dto.getCarId(), res);
                response.add(resultMap);
            } catch (Exception e) {
                log.error("Woori API CALL FAIL: " + e.getMessage());

                Map<Long, String> fail = new HashMap<>();
                fail.put(dto.getCarId(), "FAIL: " + e.getMessage());
                response.add(fail);
            }
        }

        return response;
    }

    private String makeBody(CarWooriRequestDto dto) {
        WooriRequest.DataBody dataBody = new WooriRequest.DataBody();
        dataBody.setDBPE_ANL_ICM_AM(dto.getDataBody().getDBPE_ANL_ICM_AM());
        dataBody.setCAR_PR(dto.getDataBody().getCAR_PR());

        WooriRequest body = new WooriRequest(new WooriRequest.DataHeader(), dataBody);

        try {
            return objectMapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            log.error("Woori Request body serialization fail", e);
            throw new WooriJsonSerializationException("Request body serialization fail");
        }
    }
}