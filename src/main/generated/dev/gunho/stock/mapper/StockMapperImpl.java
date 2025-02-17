package dev.gunho.stock.mapper;

import dev.gunho.rule.entity.Rule;
import dev.gunho.stock.dto.StockDTO;
import dev.gunho.stock.dto.StockDataDTO;
import dev.gunho.stock.dto.StockPagePayload;
import dev.gunho.stock.entity.Stock;
import dev.gunho.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-16T23:21:01+0900",
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
        Double buyPrice = null;
        Double sellPrice = null;
        int quantity = 0;

        symbol = stock.getSymbol();
        currency = stock.getCurrency();
        averagePrice = stock.getAveragePrice();
        buyPrice = stock.getBuyPrice();
        sellPrice = stock.getSellPrice();
        quantity = stock.getQuantity();

        Rule rule = null;
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
        Double buyPrice = null;
        Double sellPrice = null;
        if ( stockDTO != null ) {
            symbol = stockDTO.symbol();
            quantity = stockDTO.quantity();
            currency = stockDTO.currency();
            averagePrice = stockDTO.averagePrice();
            buyPrice = stockDTO.buyPrice();
            sellPrice = stockDTO.sellPrice();
        }
        User user1 = null;
        user1 = user;

        long idx = 0L;
        Rule rule = null;

        Stock stock = new Stock( idx, symbol, quantity, currency, averagePrice, buyPrice, sellPrice, user1, rule );

        return stock;
    }

    @Override
    public Stock toEntityWithRule(StockDTO stockDTO, User user, Rule rule) {
        if ( stockDTO == null && user == null && rule == null ) {
            return null;
        }

        Double buyPrice = null;
        Double sellPrice = null;
        String symbol = null;
        int quantity = 0;
        String currency = null;
        Double averagePrice = null;
        if ( stockDTO != null ) {
            buyPrice = stockDTO.buyPrice();
            sellPrice = stockDTO.sellPrice();
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

        Stock stock = new Stock( idx, symbol, quantity, currency, averagePrice, buyPrice, sellPrice, user1, rule1 );

        return stock;
    }

    protected StockDTO stockToStockDTO(Stock stock) {
        if ( stock == null ) {
            return null;
        }

        String symbol = null;
        String currency = null;
        Double averagePrice = null;
        Double buyPrice = null;
        Double sellPrice = null;
        int quantity = 0;

        symbol = stock.getSymbol();
        currency = stock.getCurrency();
        averagePrice = stock.getAveragePrice();
        buyPrice = stock.getBuyPrice();
        sellPrice = stock.getSellPrice();
        quantity = stock.getQuantity();

        StockDTO stockDTO = new StockDTO( symbol, currency, averagePrice, buyPrice, sellPrice, quantity );

        return stockDTO;
    }
}
