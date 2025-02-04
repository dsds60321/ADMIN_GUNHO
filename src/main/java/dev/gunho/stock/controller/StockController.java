package dev.gunho.stock.controller;

import dev.gunho.global.dto.UserDetail;
import dev.gunho.rule.service.RuleService;
import dev.gunho.stock.dto.StockDTO;
import dev.gunho.stock.dto.StockPagePayload;
import dev.gunho.stock.service.StockService;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Description("주식")
@Controller
@RequiredArgsConstructor
@RequestMapping("/stock")
public class StockController {

    private final StockService stockService;
    private final RuleService ruleService;

    @GetMapping("/symbols")
    public @ResponseBody ResponseEntity<?> getSymbols() {
        return stockService.getSymbols();
    }

    @GetMapping("/info")
    public @ResponseBody ResponseEntity<?> getInfo(@RequestParam(name = "symbol") String symbol) {
        return stockService.stockInfo(symbol);
    }

    @GetMapping("/form")
    public String form() {
        return "/pages/stock/form";
    }

    @GetMapping("/paging")
    public ModelAndView paging(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "condition", defaultValue = "new") String condition,
            @AuthenticationPrincipal UserDetail userDetail
    ) {
        // 페이지 요청 객체를 생성하고, 서비스 호출
        PageRequest pageRequest = PageRequest.of(page, size);
        return stockService.getPagingByCondition(new ModelAndView("/pages/stock/list"), pageRequest, userDetail.getId(), condition);
    }



    /**
     * 주식 등록
     */
    @GetMapping("/add")
    public ModelAndView addStockView(@AuthenticationPrincipal UserDetail userDetail) {
        return stockService.addView(new ModelAndView("/pages/stock/add"), userDetail.getId());
    }

    /**
     * 주식 등록
     */
    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<?> addStock(@AuthenticationPrincipal UserDetail userDetail, @RequestBody final StockDTO stockDTO) {
        return stockService.addStock(userDetail.getId(), stockDTO);
    }
}
