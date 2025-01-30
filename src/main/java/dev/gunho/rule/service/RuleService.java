package dev.gunho.rule.service;

import dev.gunho.global.dto.ApiResponse;
import dev.gunho.global.dto.ApiResponseCode;
import dev.gunho.global.dto.PagingDTO;
import dev.gunho.global.exception.GlobalException;
import dev.gunho.rule.dto.RulePayload;
import dev.gunho.rule.entity.Rule;
import dev.gunho.rule.mapper.RuleMapper;
import dev.gunho.rule.repository.RuleRepository;
import dev.gunho.stock.dto.StockDTO;
import dev.gunho.stock.entity.Stock;
import dev.gunho.stock.mapper.StockMapper;
import dev.gunho.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RuleService {

    private final RuleRepository ruleRepository;
    private final StockRepository stockRepository;

    private final RuleMapper ruleMapper;
    private final StockMapper stockMapper;

    public ModelAndView getPagingByCondition(ModelAndView mv, PageRequest pageRequest, String condition) {
        Page<Rule> rulePage = ruleRepository.findByOrderByRegDate(pageRequest);
        PagingDTO<RulePayload> pagingDTO = ruleMapper.toPagingDTO(rulePage);
        mv.addObject("PAGE", pagingDTO);
        return mv;
    }

    public ModelAndView addView(ModelAndView mv, Long userIdx) {
        List<StockDTO> stockDTOS = null;
        List<Stock> stocks = stockRepository.findAllByUserIdx(userIdx);

        if (!stocks.isEmpty()) {
            stockDTOS = stockMapper.toListDto(stocks);
        }

        mv.addObject("STOCKS", stockDTOS);
        return mv;
    }
}
