package dev.gunho.stock.controller;

import dev.gunho.stock.service.StockService;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Description("주식")
@Controller
@RequiredArgsConstructor
@RequestMapping("/stock")
public class StockController {

    private final StockService stockService;

    @GetMapping("/symbols")
    public @ResponseBody ResponseEntity<?> getSymbols() {
        return stockService.getSymbols();
    }
}
