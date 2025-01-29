package dev.gunho.stock.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.gunho.global.dto.ApiResponse;
import dev.gunho.global.service.RedisService;
import dev.gunho.global.service.WebClientService;
import dev.gunho.stock.constant.Stock;
import dev.gunho.stock.mapper.StockMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

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

    public ResponseEntity<?> getSymbols() {
        Map<Object, Object> symbols = redisService.getHashEntries(Stock.STOCK_SYMBOL_HASH);

        if (symbols == null) {
            return ApiResponse.NOT_FOUND("요청하신 데이터를 찾을수 없습니다.");
        }

        return ApiResponse.SUCCESS("", symbols);
    }
}
