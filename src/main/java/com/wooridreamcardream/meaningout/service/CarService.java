package com.wooridreamcardream.meaningout.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.wooridreamcardream.meaningout.domain.Car;
import com.wooridreamcardream.meaningout.domain.Category;

import com.wooridreamcardream.meaningout.domain.Company;
import com.wooridreamcardream.meaningout.dto.*;
import com.wooridreamcardream.meaningout.repository.CarRepository;
import com.wooridreamcardream.meaningout.repository.CategoryRepository;
import com.wooridreamcardream.meaningout.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CarService {
    private final CarRepository carRepository;
    private final CategoryRepository categoryRepository;
    private final CompanyRepository companyRepository;

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
    public List<CarWooriResponseDto> dream(String userIncome, BigDecimal minimum, BigDecimal maximum,  int people, String bodyType, String environmentalProtection, String fuelEconomy, String boycottInJapan, String patrioticCampaign, String vegan) {
        String url = "http://ec2-3-26-96-242.ap-southeast-2.compute.amazonaws.com:5000";
        WebClient webClient = WebClient.builder().baseUrl(url).build();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("people", String.valueOf(people));
        params.add("body-type", bodyType);
        params.add("e", environmentalProtection);
        params.add("f",fuelEconomy);
        params.add("b", boycottInJapan);
        params.add("p", patrioticCampaign);
        params.add("v", vegan);
        // 추천 시스템
        Mono<CarPythonResponseDto[]> response = webClient.get().uri(uriBuilder -> uriBuilder
                        .path("/refined-cars")
                        .queryParams(params)
                        .build())
                        .retrieve()
                        .bodyToMono(CarPythonResponseDto[].class);

        // 추천 시스템 json 처리
        // 1. json to list, CarPythonResponseDto
        CarPythonResponseDto[] list = response.block();

        // 2. CarPythonResponseDto to CarWooriRequestDto
        List<CarWooriRequestDto> requestDtos = new ArrayList<>();
        Map<Long, String> similarityData = new HashMap<>();
        int t_arget = 0;
        for (CarPythonResponseDto dto: list) {
            if (t_arget > 30)
                break;
            t_arget += 1;
            requestDtos.add(new CarWooriRequestDto(Long.valueOf(dto.getId()), new RequestDataBody(userIncome, dto.getAvg_price())));
            similarityData.put(Long.valueOf(dto.getId()), dto.getSimilarity());
//            System.out.println(dto.getId() + " " + dto.getSimilarity());
//            Car car = carRepository.findById(Long.valueOf(dto.getId())).orElseThrow(() -> new IllegalArgumentException("해당 북마크가 없습니다. company = " + dto.getId()));
//            System.out.println("id: " + String.valueOf(car.getId()) + ", big_title: " + car.getCategory().getCategoryName() + ", sub_title: " + car.getName());
        }

        // 1000개 -> 999, 10개 ->
        // 한도 범위 검사 open api
        final List<Map<Long, String>>[] strs = new List[]{new ArrayList<>()};

        Disposable dispose = Flux.fromIterable(requestDtos)
                .concatMap( // 객체 순서 보장
                    arg -> {
                        Mono<Map<Long, String>> result = wooriApi(arg).map(count -> {
                                    Map<Long, String> item = new HashMap<>();
                                    item.put(arg.getCarId(), count);
                                    return item;
                                });
                        return result;
                })
                .collectList()
                .subscribe((data) -> {
                    strs[0] = data;});

        while (true){
            if (dispose.isDisposed()) {
                System.out.println(strs[0]);
                break;
            }
            else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("check scope");
        List<Map<Long, String>> objects = strs[0];
        List<Long> possible_ids = new ArrayList<>();
        Map<Long, BigDecimal> loanData = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        for (Map<Long, String> object: objects) {

            object.forEach((key, value)->
            {
                try {
                    System.out.println(key + value);
                    Map<String, Object> m_ap = new HashMap<>();
                    m_ap = mapper.readValue(value, new TypeReference<Map<String, Object>>(){});
                    Map<String, String> m__ap = mapper.convertValue(m_ap.get("dataBody"), Map.class);

                    BigDecimal LN_AVL_AM = new BigDecimal(m__ap.get("LN_AVL_AM"));
                    if (LN_AVL_AM.compareTo(minimum) > 0 && LN_AVL_AM.compareTo(maximum) < 0) {

                        System.out.println(LN_AVL_AM);
                        possible_ids.add(key);
                        loanData.put(key, LN_AVL_AM);
                    }

                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
        }
        int target = 0;

        List<CarResponseDto> carInIds = carRepository.findByIdIn(possible_ids).stream().map(CarResponseDto::new).collect(Collectors.toList());
        List<CarWooriResponseDto> carWooriResponseDtos = new ArrayList<>();

        for (CarResponseDto carResponseDto: carInIds) {
            Company company = companyRepository.findByName(carResponseDto.getCompany()).orElseThrow(() -> new IllegalArgumentException("해당 북마크가 없습니다. company = " + carResponseDto.getCompany()));
            if (target > 9)
                break;
            target += 1;
            carWooriResponseDtos.add(new CarWooriResponseDto(carResponseDto, similarityData.get(carResponseDto.getId()), new BigDecimal(102113030), company.getImageUrl()));
        }
        return carWooriResponseDtos;
    }

    public Mono<String> wooriApi(CarWooriRequestDto requestDto) {
        String url = "https://openapi.wooribank.com:444";
        WebClient webClient = WebClient.builder().baseUrl(url).build();
        String body = "{\n" +
        "  \"dataHeader\": {\n" +
        "    \"UTZPE_CNCT_IPAD\": \"\",\n" +
        "    \"UTZPE_CNCT_MCHR_UNQ_ID\": \"\",\n" +
        "    \"UTZPE_CNCT_TEL_NO_TXT\": \"\",\n" +
        "    \"UTZPE_CNCT_MCHR_IDF_SRNO\": \"\",\n" +
        "    \"UTZ_MCHR_OS_DSCD\": \"\",\n" +
        "    \"UTZ_MCHR_OS_VER_NM\": \"\",\n" +
        "    \"UTZ_MCHR_MDL_NM\": \"\",\n" +
        "    \"UTZ_MCHR_APP_VER_NM\": \"\"\n" +
        "  },\n" +
        "  \"dataBody\": {\n" +
        "    \"DBPE_ANL_ICM_AM\": \"" + requestDto.getDataBody().getDBPE_ANL_ICM_AM()+ "\",\n" +
        "    \"GRN_NCAR_YN\": \"Y\",\n" +
        "    \"CAR_PR\": \"" + requestDto.getDataBody().getCAR_PR() +"\",\n" +
        "    \"CRINF_INQ_AGR_YN\": \"Y\",\n" +
        "    \"INF_OFR_MND_AGR_YN\": \"Y\",\n" +
        "    \"GAT_UTZ_MND_AGR_YN\": \"Y\",\n" +
        "    \"CUS_IDF_INF_AGR_YN\": \"Y\",\n" +
        "    \"INF_OFR_CHC_AGR_YN\": \"Y\",\n" +
        "    \"GAT_UTZ_CHC_AGR_YN\": \"Y\"\n" +
        "  }\n" +
        "}";

        return webClient.post()
                .uri("/oai/wb/v1/newcar/getNewCarLoanAm")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers -> {
                    headers.add("appkey", "l7xxDPvgxEKY9hmvslvoN4Hj3IaJ3REkmqXD");
                })
                .body(BodyInserters.fromValue(body))
                .retrieve()
                .bodyToMono(String.class);
    }
}
