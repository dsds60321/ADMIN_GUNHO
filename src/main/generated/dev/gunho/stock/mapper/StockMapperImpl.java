package dev.gunho.stock.mapper;

import dev.gunho.rule.dto.RuleDto;
import dev.gunho.rule.entity.Rule;
import dev.gunho.stock.dto.StockDTO;
import dev.gunho.stock.dto.StockDataDTO;
import dev.gunho.stock.dto.StockPagePayload;
import dev.gunho.stock.entity.Stock;
import dev.gunho.user.entity.User;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-25T23:16:58+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class StockMapperImpl implements StockMapper {

    @Override
    public StockPagePayload toDTO(Stock stock) {
        if ( stock == null ) {
            return null;
        }

        String symbol = null;
        String currency = null;
        Double averagePrice = null;
        int quantity = 0;
        RuleDto rule = null;

        symbol = stock.getSymbol();
        currency = stock.getCurrency();
        averagePrice = stock.getAveragePrice();
        quantity = stock.getQuantity();
        rule = ruleToRuleDto( stock.getRule() );

        Double buyPrice = null;
        Double sellPrice = null;
        Double marketPrice = null;

        StockPagePayload stockPagePayload = new StockPagePayload( symbol, currency, averagePrice, buyPrice, sellPrice, marketPrice, quantity, rule );

        return stockPagePayload;
    }

    @Override
    public List<StockDTO> toListDto(List<Stock> stock) {
        if ( stock == null ) {
            return null;
        }

        List<StockDTO> list = new ArrayList<StockDTO>( stock.size() );
        for ( Stock stock1 : stock ) {
            list.add( stockToStockDTO( stock1 ) );
        }

        return list;
    }

    @Override
    public StockDataDTO toStockDataDTO(String symbol, StockDataDTO.StockPriceData stockPriceData) {
        if ( symbol == null && stockPriceData == null ) {
            return null;
        }

        StockDataDTO stockDataDTO = new StockDataDTO();

        stockDataDTO.setSymbol( symbol );
        stockDataDTO.setStockPriceData( stockPriceData );

        return stockDataDTO;
    }

    @Override
    public Stock toEntity(StockDTO stockDTO, User user) {
        if ( stockDTO == null && user == null ) {
            return null;
        }

        String symbol = null;
        int quantity = 0;
        String currency = null;
        Double averagePrice = null;
        if ( stockDTO != null ) {
            symbol = stockDTO.symbol();
            quantity = stockDTO.quantity();
            currency = stockDTO.currency();
            averagePrice = stockDTO.averagePrice();
        }
        User user1 = null;
        user1 = user;

        long idx = 0L;
        Rule rule = null;

        Stock stock = new Stock( idx, symbol, quantity, currency, averagePrice, user1, rule );

        return stock;
    }

    @Override
    public Stock toEntityWithRule(StockDTO stockDTO, User user, Rule rule) {
        if ( stockDTO == null && user == null && rule == null ) {
            return null;
        }

        String symbol = null;
        int quantity = 0;
        String currency = null;
        Double averagePrice = null;
        if ( stockDTO != null ) {
            symbol = stockDTO.symbol();
            quantity = stockDTO.quantity();
            currency = stockDTO.currency();
            averagePrice = stockDTO.averagePrice();
        }
        User user1 = null;
        user1 = user;
        Rule rule1 = null;
        rule1 = rule;

        long idx = 0L;

        Stock stock = new Stock( idx, symbol, quantity, currency, averagePrice, user1, rule1 );

        return stock;
    }

    protected RuleDto ruleToRuleDto(Rule rule) {
        if ( rule == null ) {
            return null;
        }

        long idx = 0L;
        String id = null;
        int buyPrice = 0;
        BigDecimal buyPercentage = null;
        int sellPrice = 0;
        BigDecimal sellPercentage = null;

        idx = rule.getIdx();
        id = rule.getId();
        buyPrice = rule.getBuyPrice();
        buyPercentage = rule.getBuyPercentage();
        sellPrice = rule.getSellPrice();
        sellPercentage = rule.getSellPercentage();

        String symbol = null;

        RuleDto ruleDto = new RuleDto( idx, id, symbol, buyPrice, buyPercentage, sellPrice, sellPercentage );

        return ruleDto;
    }

    protected StockDTO stockToStockDTO(Stock stock) {
        if ( stock == null ) {
            return null;
        }

        String symbol = null;
        String currency = null;
        Double averagePrice = null;
        int quantity = 0;

        symbol = stock.getSymbol();
        currency = stock.getCurrency();
        averagePrice = stock.getAveragePrice();
        quantity = stock.getQuantity();

        Double buyPrice = null;
        Double sellPrice = null;

        StockDTO stockDTO = new StockDTO( symbol, currency, averagePrice, buyPrice, sellPrice, quantity );

        return stockDTO;
    }
}
