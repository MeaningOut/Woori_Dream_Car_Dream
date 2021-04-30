package com.wooridreamcardream.meaningout.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wooridreamcardream.meaningout.domain.Car;
import com.wooridreamcardream.meaningout.domain.Category;

import com.wooridreamcardream.meaningout.dto.CarResponseDto;
import com.wooridreamcardream.meaningout.dto.CarSaveRequestDto;
import com.wooridreamcardream.meaningout.dto.CategorySaveRequestDto;
import com.wooridreamcardream.meaningout.repository.CarRepository;
import com.wooridreamcardream.meaningout.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CarService {
    private final CarRepository carRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public List<CarResponseDto> findByMinPriceAndMaxPrice(BigDecimal minPrice, BigDecimal maxPrice) {
        return carRepository.findByMinPriceGreaterThanEqualAndMaxPriceLessThanEqual(minPrice, maxPrice).stream().map(CarResponseDto::new).collect(Collectors.toList());
    }
    @Transactional
    public List<CarResponseDto> findAll() {
        return carRepository.findAll().stream().map(CarResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public CarResponseDto findById(Long id) {
        Car entity = carRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 자동차가 없습니다. id = " + id));
        return new CarResponseDto(entity);
    }

    @Transactional
    public Long save(CarSaveRequestDto carSaveRequestDto) {
        Category category = categoryRepository.findByCategoryName(carSaveRequestDto.getCategoryName())
                .orElseGet(() -> categoryRepository.save(new CategorySaveRequestDto(carSaveRequestDto.getCategoryName()).toEntity()));

        Car car = carRepository.findByCategoryIdAndName(category.getId(), carSaveRequestDto.getName())
                .orElseGet(() -> carRepository.save(carSaveRequestDto.toEntity(category)));
        return car.getId();
    }
    @Transactional
    public void dream(BigDecimal userIncome, int people, String bodyType, String environmentalProtection, String fuelEconomy, String boycottInJapan, String patrioticCampaign, String vegan) {
        String url = "http://localhost:5000";
        WebClient webClient = WebClient.builder().baseUrl(url).build();
//        WebClient webClient = WebClient.create(url);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("people", String.valueOf(people));
        params.add("body-type", bodyType);
        params.add("environmental-protection", environmentalProtection);
        params.add("fuel-economy",fuelEconomy);
        params.add("boycott-in-japan", boycottInJapan);
        params.add("patriotic-campaign", patrioticCampaign);
        params.add("vegan", vegan);
        // 추천 시스템
        String response =
                webClient.get().uri(uriBuilder -> uriBuilder
                        .path("/refined-cars")
                        .queryParams(params)
                        .build())
                        .retrieve().bodyToMono(String.class)
                .block();

        // 추천 시스템 json 처리
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = new HashMap<>();
        try {
            map = mapper.readValue(response, Map.class);
            System.out.println(map);
        } catch(IOException e) {
            e.printStackTrace();
        }
        List<Long> userIds = new ArrayList<>();
        userIds.add(1L);
        userIds.add(2L);
        // 한도 범위 검사
        Flux<CarResponseDto> flux = Flux.fromIterable(userIds)
                .parallel()
                .runOn(Schedulers.elastic())
                .flatMap(this::test)
                .ordered((u1, u2) -> Long.valueOf(u2.getId() - u1.getId()).intValue());

    }
    public Mono<CarResponseDto> test(Long id) {
        String url = "http://localhost:8080";
        WebClient webClient = WebClient.builder().baseUrl(url).build();

        return webClient.get()
                .uri("/api/v1/car/{id}", id)
                .retrieve()
                .bodyToMono(CarResponseDto.class);
    }
//    public Map<String, String> getScope(BigDecimal userIncome, BigDecimal carPrice) {
//        ObjectMapper mapper = new ObjectMapper();
//        String url = "https://openapi.wooribank.com:444/oai/wb/v1/newcar/getNewCarLoanAm";
//
//        WebClient webClient = WebClient.builder().baseUrl(url).build();
//        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
//        params.add("people", String.valueOf());
//        try {
//            Map<String, String> map = mapper.readValue(response, Map.class);
//
//        }
//    }
}
