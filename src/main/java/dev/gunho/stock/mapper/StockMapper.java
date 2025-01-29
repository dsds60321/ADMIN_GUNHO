package dev.gunho.stock.mapper;

import dev.gunho.stock.dto.StockDataDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StockMapper {

    StockMapper INSTANCE = Mappers.getMapper(StockMapper.class);


    @Mapping(target = "stockPriceData", source = "stockPriceData")
    StockDataDTO toStockDataDTO(String symbol, StockDataDTO.StockPriceData stockPriceData);

}
