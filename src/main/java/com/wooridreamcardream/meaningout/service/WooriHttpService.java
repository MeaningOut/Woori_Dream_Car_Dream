package com.wooridreamcardream.meaningout.service;

import com.wooridreamcardream.meaningout.dto.car.CarWooriRequestDto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WooriHttpService {

    static final String HEADER_VALUE = "l7xxDPvgxEKY9hmvslvoN4Hj3IaJ3REkmqXD";
    static final String appKey = "appkey";
    static final String url = "/oai/wb/v1/newcar/getNewCarLoanAm";
    static final int wooriList = 0;
    static final String flaskURL = "http://127.0.0.1:5000";

    private final WebClient webClient;

    public WooriHttpService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(flaskURL).build();
    }

    /**
     *
     *
     * @param dto 우리은행 API에 보낼 데이터
     * @return
     */
    public Mono<String> readyToWooriApi(CarWooriRequestDto dto) {
        String requestBody = makeBody(dto);

        return webClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers -> {
                    headers.add(appKey, HEADER_VALUE);
                })
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(String.class);
    }

    public List<Map<Long, String>> request(List<CarWooriRequestDto> dto) {
        final List<Map<Long, String>>[] response = new List[]{new ArrayList()};
        Disposable dispose = callWooriApi(dto, response);

        while (true){
            if (!dispose.isDisposed()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                //System.out.println("===========ERROR CHECK==========");
                break;
            }
        }
        return response[wooriList];
    }

    /**
     *
     * 비동기 요청을 보낸다.
     * concatMap을 사용해서 응답 순서를 보장한다.
     *
     * @param dto
     * @return
     */
    private Disposable callWooriApi(List<CarWooriRequestDto> dto, List<Map<Long, String>>[] response) {

        return Flux.fromIterable(dto)
                .concatMap( // 객체 순서 보장
                        arg -> {
                            Mono<Map<Long, String>> result = readyToWooriApi(arg).map(res -> {
                                Map<Long, String> item = new HashMap<>();
                                item.put(arg.getCarId(), res);
                                return item;
                            });
                            return result;
                        })
                .collectList()
                .subscribe((data) -> {
                    response[wooriList] = data;
                });
    }

    private String makeBody(CarWooriRequestDto dto) {
        return "{\n" +
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
    }
}