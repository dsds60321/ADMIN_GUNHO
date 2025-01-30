package dev.gunho.stock.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.gunho.global.dto.ApiResponse;
import dev.gunho.global.dto.ApiResponseCode;
import dev.gunho.global.dto.PagingDTO;
import dev.gunho.global.exception.GlobalException;
import dev.gunho.global.service.RedisService;
import dev.gunho.global.service.WebClientService;
import dev.gunho.rule.entity.Rule;
import dev.gunho.rule.repository.RuleRepository;
import dev.gunho.stock.constant.StockConstants;
import dev.gunho.stock.dto.StockDTO;
import dev.gunho.stock.dto.StockPagePayload;
import dev.gunho.stock.entity.Stock;
import dev.gunho.stock.entity.StockSymbol;
import dev.gunho.stock.mapper.StockMapper;
import dev.gunho.stock.repository.StockRepository;
import dev.gunho.stock.repository.StockSymbolRepository;
import dev.gunho.user.entity.User;
import dev.gunho.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockService {

    private final UserRepository userRepository;
    @Value("${api.alpha-vantage.host}")
    private String alphaVantageApi;

    @Value("${api.alpha-vantage.key}")
    private String alphaVantageApiKey;

    private final StockSymbolRepository stockSymbolRepository;
    private final StockMapper stockMapper = StockMapper.INSTANCE;
    private final StockRepository stockRepository;
    private final RuleRepository ruleRepository;

    private final RedisService redisService;


    public ResponseEntity<?> getSymbols() {
        Map<Object, Object> symbols = redisService.getHashEntries(StockConstants.STOCK_SYMBOL_HASH);

        if (symbols == null) {
            return ApiResponse.NOT_FOUND("요청하신 데이터를 찾을수 없습니다.");
        }

        return ApiResponse.SUCCESS("", symbols);
    }

    public ResponseEntity<?> stockInfo(String symbol) {
        StockSymbol stockSymbol = stockSymbolRepository.findBySymbol(symbol)
                .orElseThrow(() -> new GlobalException(ApiResponseCode.NOT_FOUND));

        return ApiResponse.SUCCESS(stockSymbol);
    }

    public ModelAndView addView(ModelAndView mv, Long id) {
        List<Rule> ruleList = ruleRepository.findAllByUser_Idx(id);
        mv.addObject("RULE", ruleList);
        return mv;
    }

    @Transactional
    public ResponseEntity<?> addStock(Long userIdx, StockDTO stockDTO) {
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new GlobalException(ApiResponseCode.NOT_FOUND));

        Stock stock = stockMapper.toEntity(stockDTO, user);
        Stock saveStock = stockRepository.save(stock);

        return saveStock.getIdx() > 0 ? ApiResponse.SUCCESS("저장이 완료되었습니다.") : ApiResponse.SERVER_ERROR();
    }

    @Transactional
    public ModelAndView getPagingByCondition(ModelAndView mv, PageRequest pageRequest, String condition) {
        Page<Stock> stockPage = stockRepository.findByOrderByRegDate(pageRequest);
        PagingDTO<StockDTO> pagingDTO = stockMapper.toPagingDTO(stockPage);
        mv.addObject("PAGE", pagingDTO);
        return mv;
    }
}
