package dev.gunho.stock.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.gunho.global.dto.ApiResponse;
import dev.gunho.global.dto.ApiResponseCode;
import dev.gunho.global.service.RedisService;
import dev.gunho.global.service.WebClientService;
import dev.gunho.stock.constant.StockRedisKeys;
import dev.gunho.stock.dto.StockDataDTO;
import dev.gunho.stock.mapper.StockMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockService {

    @Value("${api.alpha-vantage.host}")
    private String alphaVantageApi;

    @Value("${api.alpha-vantage.key}")
    private String alphaVantageApiKey;

    private final RedisService redisService;
    private final WebClientService webClientService;
    private final ObjectMapper objectMapper;

    private final StockMapper stockMapper = StockMapper.INSTANCE;


    public String Intraday() {
        LinkedMultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("function", "TIME_SERIES_INTRADAY");
        queryParams.add("symbol", "IBM");
        queryParams.add("interval", "5min");
        queryParams.add("apikey", alphaVantageApiKey);

        try {

            String request = webClientService.get(alphaVantageApi, "/query", queryParams, null, String.class);

            if (request == null) {
                return "";
            }

            log.info("request: {}", request);
            return request;
        } catch (Exception e) {
            log.error("StockService.getInt Exception: {}", e.getMessage());
        }

        return "";
    }

    public ResponseEntity<?> getTopStockData() {
        List<StockDataDTO> stockDataDTOS = new ArrayList<>();
        List<String> topStockSymbols = redisService.getRange(StockRedisKeys.STOCK_SYMBOL_LIST, 0, 10);

        if (topStockSymbols == null || topStockSymbols.isEmpty()) {
            return ApiResponse.SERVER_ERROR(ApiResponseCode.NOT_FOUND.getCode(), "요청한 데이터가 없습니다");
        }

        for (String symbol : topStockSymbols) {
            try {
                // Redis Hash를 조회
                Map<Object, Object> hashEntries = redisService.getHashEntries(symbol);
                for (Map.Entry<Object, Object> entry : hashEntries.entrySet()) {
                    // Entry 키와 Raw JSON 데이터
                    String date = entry.getKey().toString();
                    String rawValue = entry.getValue().toString();

                    // JSON 키 정리
                    String cleanedJson = cleanJsonKeys(rawValue);

                    // JSON 데이터를 StockPriceData로 매핑
                    StockDataDTO.StockPriceData stockPriceData = objectMapper.readValue(cleanedJson, StockDataDTO.StockPriceData.class);
                    stockPriceData.setDate(date);

                    // MapStruct를 통해 StockDataDTO 생성
                    StockDataDTO stockDataDTO = stockMapper.toStockDataDTO(symbol, stockPriceData);

                    // 결과 리스트에 추가
                    stockDataDTOS.add(stockDataDTO);
                }

                getAverageCloseForLastYear(stockDataDTOS);

            } catch (JsonProcessingException e) {
                log.error("Failed to process Redis data for symbol: {}", symbol, e);
                return ApiResponse.SERVER_ERROR(ApiResponseCode.INTERNAL_SERVER_ERROR.getCode(), "데이터 처리 중 오류가 발생했습니다");
            }
        }




        return ApiResponse.SUCCESS("요청에 성공 했습니다.", stockDataDTOS);
    }


    private String cleanJsonKeys(String rawJson) {
        try {
            // JSON 데이터를 ObjectNode로 읽어들임
            ObjectNode jsonNode = (ObjectNode) objectMapper.readTree(rawJson);

            // 새로운 ObjectNode 생성 (key -> cleanKey 변환)
            ObjectNode cleanedNode = objectMapper.createObjectNode();
            jsonNode.fields().forEachRemaining(entry -> {
                String cleanedKey = entry.getKey().replaceAll("^\\d+\\.\\s", ""); // "1. open" -> "open"
                cleanedNode.set(cleanedKey, entry.getValue());
            });

            // 정리된 JSON 데이터를 문자열로 반환
            return objectMapper.writeValueAsString(cleanedNode);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to clean JSON keys", e);
        }
    }

    private Map<String, Double> getAverageCloseForLastYear(List<StockDataDTO> stockDataDTOS) {
        // 현재 날짜 기준 1년 전 날짜 계산
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Symbol별로 데이터를 그룹화 후, 1년 내 Close 평균 계산
        return stockDataDTOS.stream()
                .filter(dto -> {
                    // date 필터링: 1년 이내 데이터만 사용
                    LocalDate date = LocalDate.parse(dto.getStockPriceData().getDate(), formatter);
                    return date.isAfter(oneYearAgo) || date.isEqual(oneYearAgo);
                })
                .collect(Collectors.groupingBy(
                        StockDataDTO::getSymbol,
                        Collectors.averagingDouble(dto -> dto.getStockPriceData().getClose())
                ));
    }



//    최근
//    https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=IBM&interval=5min&apikey=demo

//    일별
//    https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=IBM&apikey=demo

}
