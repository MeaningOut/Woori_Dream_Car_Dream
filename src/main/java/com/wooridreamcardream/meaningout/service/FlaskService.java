package com.wooridreamcardream.meaningout.service;

import com.wooridreamcardream.meaningout.dto.car.CarPythonResponseDto;
import com.wooridreamcardream.meaningout.dto.car.CarWooriRequestDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.reactive.function.BodyInserters.*;

@Service
public class FlaskService {
    private final WebClient webClient;

    public FlaskService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://127.0.0.1:5000").build();
    }

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
    public Mono<String> wooriApi(CarWooriRequestDto dto) {
//        MultipartBodyBuilder builder = new MultipartBodyBuilder();
//        builder.part("dataHeader",
//                fromFormData("UTZPE_CNCT_IPAD", "")
//                        .with("UTZPE_CNCT_MCHR_UNQ_ID", "")
//                        .with("UTZPE_CNCT_TEL_NO_TXT", "")
//                        .with("UTZPE_CNCT_MCHR_IDF_SRNO", "")
//                        .with("UTZ_MCHR_OS_DSCD", "")
//                        .with("UTZ_MCHR_OS_VER_NM","")
//                        .with("UTZ_MCHR_MDL_NM","")
//                        .with("UTZ_MCHR_APP_VER_NM",""));
//        builder.part("dataBody",
//                fromFormData("DBPE_ANL_ICM_AM", dto.getDataBody().getDBPE_ANL_ICM_AM())
//                        .with("GRN_NCAR_YN", "Y")
//                        .with("CAR_PR", dto.getDataBody().getCAR_PR())
//                        .with("CRINF_INQ_AGR_YN", "Y")
//                        .with("INF_OFR_MND_AGR_YN", "Y")
//                        .with("GAT_UTZ_MND_AGR_YN", "Y")
//                        .with("CUS_IDF_INF_AGR_YN", "Y")
//                        .with("INF_OFR_CHC_AGR_YN", "Y")
//                        .with("GAT_UTZ_CHC_AGR_YN", "Y"));
//        MultiValueMap<String, HttpEntity<?>> multipartBody = builder.build();
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
                "    \"DBPE_ANL_ICM_AM\": \"" + dto.getDataBody().getDBPE_ANL_ICM_AM()+ "\",\n" +
                "    \"GRN_NCAR_YN\": \"Y\",\n" +
                "    \"CAR_PR\": \"" + dto.getDataBody().getCAR_PR() +"\",\n" +
                "    \"CRINF_INQ_AGR_YN\": \"Y\",\n" +
                "    \"INF_OFR_MND_AGR_YN\": \"Y\",\n" +
                "    \"GAT_UTZ_MND_AGR_YN\": \"Y\",\n" +
                "    \"CUS_IDF_INF_AGR_YN\": \"Y\",\n" +
                "    \"INF_OFR_CHC_AGR_YN\": \"Y\",\n" +
                "    \"GAT_UTZ_CHC_AGR_YN\": \"Y\"\n" +
                "  }\n" +
                "}";
//        return webClient.post()
//                .uri("/oai/wb/v1/newcar/getNewCarLoanAm")
//                .body(fromObject(multipartBody))
//                .retrieve()
//                .bodyToMono(String.class);
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
//    public List<Map<Long, String>>[] api(List<CarWooriRequestDto> dto) {
//        final List<Map<Long, String>>[] strs = new List[]{new ArrayList<>()};
//
//        Disposable dispose = Flux.fromIterable(dto)
//                .concatMap( // 객체 순서 보장
//                    arg -> {
//                        Mono<Map<Long, String>> result = wooriApi(arg).map(count -> {
//                                    Map<Long, String> item = new HashMap<>();
//                                    item.put(arg.getCarId(), count);
//                                    return item;
//                                });
//                        return result;
//                })
//                .collectList()
//                .subscribe((data) -> {
//                    strs[0] = data;});
//
//        while (true){
//            if (dispose.isDisposed()) {
//                System.out.println(strs[0]);
//                break;
//            }
//            else {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
}
