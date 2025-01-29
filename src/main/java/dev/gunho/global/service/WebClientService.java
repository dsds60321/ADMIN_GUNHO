package dev.gunho.global.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebClientService {

    private final WebClient.Builder webClientBuilder;

    /**
     * 특정 Base URL에 대한 WebClient 생성
     */
    public WebClient getWebClient(String baseUrl) {
        return webClientBuilder
                .baseUrl(baseUrl)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.add("User-Agent", "Custom-WebClient"); // 공통 헤더
                })
                .build();
    }

    /**
     * GET 요청 처리
     * @param baseUrl Base URL
     * @param uri 요청 URI
     * @param params QueryString 파라미터
     * @param headers 요청 헤더
     * @param returnClass 반환 타입
     * @return 응답 객체
     */
    public <T> T get(String baseUrl, String uri, MultiValueMap<String, String> params, Map<String, String> headers, Class<T> returnClass) {
        try {
            return getWebClient(baseUrl)
                    .get()
                    .uri(uriBuilder -> uriBuilder.path(uri).queryParams(params).build())
                    .headers(httpHeaders -> {
                        if (headers != null) {
                            httpHeaders.setAll(headers);
                        }
                    })
                    .accept(MediaType.APPLICATION_JSON)
                    .acceptCharset(UTF_8)
                    .retrieve()
                    .onStatus(status ->
                            status.is4xxClientError()
                                    || status.is5xxServerError(), clientResponse -> {
                        log.error("[WebClientService] HTTP 요청 실패: statusCode={}, uri={}", clientResponse.statusCode(), uri);
                        return clientResponse.createException();
                    })
                    .bodyToMono(returnClass)
                    .blockOptional()
                    .orElse(null);
        } catch (Exception e) {
            log.error("[WebClientService] GET 요청 중 오류 발생: ", e);
            return null;
        }
    }

    /**
     * POST 요청 처리
     * @param baseUrl Base URL
     * @param uri 요청 URI
     * @param body 요청 Body
     * @param headers 요청 헤더
     * @param returnClass 반환 타입
     * @return 응답 객체
     */
    public <T> T post(String baseUrl, String uri, Object body, Map<String, String> headers, Class<T> returnClass) {
        try {
            return getWebClient(baseUrl)
                    .post()
                    .uri(uri)
                    .headers(httpHeaders -> {
                        if (headers != null) {
                            httpHeaders.setAll(headers);
                        }
                    })
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .acceptCharset(UTF_8)
                    .bodyValue(body)
                    .retrieve()
                    .onStatus(status ->
                            status.is4xxClientError()
                                    || status.is5xxServerError(), clientResponse -> {
                        log.error("[WebClientService] HTTP 요청 실패: statusCode={}, uri={}", clientResponse.statusCode(), uri);
                        return clientResponse.createException();
                    })
                    .bodyToMono(returnClass)
                    .blockOptional()
                    .orElse(null);
        } catch (Exception e) {
            log.error("[WebClientService] POST 요청 중 오류 발생: ", e);
            return null;
        }
    }


}
