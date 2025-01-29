package dev.gunho.global.commandrunner;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.gunho.global.service.RedisService;
import dev.gunho.stock.entity.QStockSymbol;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

import static dev.gunho.stock.constant.StockRedisKeys.STOCK_SYMBOL_LIST;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final RedisService redisService;
    private final JPAQueryFactory queryFactory;

    @Override
    public void run(String... args) throws Exception {

        try {
            QStockSymbol stockSymbol = QStockSymbol.stockSymbol;
            List<String> symbols = queryFactory
                    .select(stockSymbol.symbol)
                    .from(stockSymbol)
                    .orderBy(stockSymbol.marketCap.desc())
                    .fetch();

            if (symbols.isEmpty()) {
                log.info("No stock symbols found");
                return;
            }

            if (redisService.hasKey(STOCK_SYMBOL_LIST)) {
                redisService.delete(STOCK_SYMBOL_LIST);
            }

            redisService.addAllToList(STOCK_SYMBOL_LIST, symbols);
            log.info("Stock symbols loaded");
        } catch (Exception e) {
            log.error("DataLoader run Exception: {}", e.getMessage());
        }

    }
}
