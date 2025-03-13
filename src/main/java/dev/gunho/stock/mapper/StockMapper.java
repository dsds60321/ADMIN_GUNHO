package dev.gunho.stock.mapper;

import dev.gunho.global.mapper.BaseMapper;
import dev.gunho.rule.entity.Rule;
import dev.gunho.stock.dto.StockDTO;
import dev.gunho.stock.dto.StockDataDTO;
import dev.gunho.stock.dto.StockPagePayload;
import dev.gunho.stock.entity.Stock;
import dev.gunho.user.entity.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface StockMapper extends BaseMapper<Stock, StockPagePayload>
{

    StockMapper INSTANCE = Mappers.getMapper(StockMapper.class);

    @Override
//    @Mappings({@Mapping(target = "rule", ignore = true)})
    StockPagePayload toDTO(Stock stock);

    @Mappings({@Mapping(target = "rule", ignore = true)})
    List<StockDTO> toListDto(List<Stock> stock);



    @Mapping(target = "stockPriceData", source = "stockPriceData")
    StockDataDTO toStockDataDTO(String symbol, StockDataDTO.StockPriceData stockPriceData);

    @Mappings({
            @Mapping(target = "idx", ignore = true),
            @Mapping(target = "rule", ignore = true),
            @Mapping(target = "user", source = "user")
    })
    Stock toEntity(StockDTO stockDTO, User user);

    @Mappings({
            @Mapping(target = "idx", ignore = true),
            @Mapping(target = "rule", source = "rule"),
            @Mapping(target = "user", source = "user"),
    })
    Stock toEntityWithRule(StockDTO stockDTO, User user, Rule rule);


}
