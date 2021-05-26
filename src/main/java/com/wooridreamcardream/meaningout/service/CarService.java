package com.wooridreamcardream.meaningout.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wooridreamcardream.meaningout.domain.Car;
import com.wooridreamcardream.meaningout.domain.Category;

import com.wooridreamcardream.meaningout.domain.Company;
import com.wooridreamcardream.meaningout.dto.*;
import com.wooridreamcardream.meaningout.dto.car.*;
import com.wooridreamcardream.meaningout.repository.CarRepository;
import com.wooridreamcardream.meaningout.repository.CategoryRepository;
import com.wooridreamcardream.meaningout.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CarService {
    private final CarRepository carRepository;
    private final CategoryRepository categoryRepository;
    private final CompanyRepository companyRepository;

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
        // company 객체가 있는지 확인하는 부분 isValidCompany로 뺄 것.
        Company company = companyRepository.findByName(carSaveRequestDto.getCompanyName())
                .orElseGet(() -> companyRepository.save(new CompanySaveRequestDto(carSaveRequestDto.getCompanyName()).toEntity()));
        // category 객체가 있는지 확인하는 부분 isValidCategory로 뺄 것.
        Category category = categoryRepository.findByName(carSaveRequestDto.getCategoryName())
                .orElseGet(() -> categoryRepository.save(new CategorySaveRequestDto(carSaveRequestDto.getCategoryName()).toEntity()));

        Car car = carRepository.findByCategoryIdAndName(category.getId(), carSaveRequestDto.getName())
                .orElseGet(() -> carRepository.save(carSaveRequestDto.toEntity(category, company)));
        return car.getId();
    }
    @Transactional
    public List<CarWooriResponseDto> dream(String userIncome, BigDecimal minimum, BigDecimal maximum, int people, String bodyType, String environmentalProtection, String fuelEconomy, String boycottInJapan, String patrioticCampaign, String vegan) {

        // 추천 시스템 요청 부분
        String url = "http://127.0.0.1:5000";
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

        // 여기까지

        // 2. CarPythonResponseDto to CarWooriRequestDto
        List<CarWooriRequestDto> requestDtos = new ArrayList<>();
        Map<Long, String> similarityData = new HashMap<>();
        for (CarPythonResponseDto dto: list) {
            requestDtos.add(new CarWooriRequestDto(Long.valueOf(dto.getId()), new RequestDataBody(userIncome, dto.getAvg_price())));
            similarityData.put(Long.valueOf(dto.getId()), dto.getSimilarity());
        }

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

        List<Map<Long, String>> objects = strs[0];
        List<Long> possible_ids = new ArrayList<>();
        Map<Long, BigDecimal> loanData = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        for (Map<Long, String> object: objects) {

            object.forEach((key, value)->
            {
                try {
                    Map<String, Object> m_ap = new HashMap<>();
                    m_ap = mapper.readValue(value, new TypeReference<Map<String, Object>>(){});
                    Map<String, String> m__ap = mapper.convertValue(m_ap.get("dataBody"), Map.class);

//                  API로부터 반환받은 대출 한도 금액(LN_AVL_AM)이 사용자가 입력한 대출 범위에 있는지 확인
                    BigDecimal LN_AVL_AM = new BigDecimal(m__ap.get("LN_AVL_AM"));
                    if (LN_AVL_AM.compareTo(minimum) > 0 && LN_AVL_AM.compareTo(maximum) < 0) {
//                      범위에 들어가면 possible_ids에 추가
                        possible_ids.add(key);
                        loanData.put(key, LN_AVL_AM);
                    }

                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
        }

        List<CarResponseDto> carInIds = carRepository.findByIdIn(possible_ids).stream().map(CarResponseDto::new).collect(Collectors.toList());
        List<CarWooriResponseDto> carWooriResponseDtos = new ArrayList<>();

        for (CarResponseDto carResponseDto: carInIds) {
            Company company = companyRepository.findByName(carResponseDto.getCompany().getName()).orElseThrow(() -> new IllegalArgumentException("해당 북마크가 없습니다. company = " + carResponseDto.getCompany()));
            carWooriResponseDtos.add(new CarWooriResponseDto(carResponseDto, similarityData.get(carResponseDto.getId()), loanData.get(carResponseDto.getId()), company.getLogo()));
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
