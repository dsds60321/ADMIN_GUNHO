package dev.gunho.stock.mapper;

import dev.gunho.stock.dto.StockDataDTO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-29T13:15:24+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.5 (Amazon.com Inc.)"
)
public class StockMapperImpl implements StockMapper {

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
}
