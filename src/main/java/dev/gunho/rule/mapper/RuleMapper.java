package dev.gunho.rule.mapper;

import dev.gunho.global.mapper.BaseMapper;
import dev.gunho.rule.dto.RuleDto;
import dev.gunho.rule.dto.RulePayload;
import dev.gunho.rule.entity.Rule;
import dev.gunho.stock.entity.Stock;
import dev.gunho.user.entity.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface RuleMapper extends BaseMapper<Rule, RulePayload> {

    RuleMapper INSTANCE = Mappers.getMapper(RuleMapper.class);

    @Mappings({
            @Mapping(target = "buyPrice", source = "rule.buyPrice"),
            @Mapping(target = "sellPrice", source = "rule.sellPrice"),
    })
    RulePayload toDTO(Rule rule, Stock stock);


    @Mappings({
            @Mapping(target = "idx", ignore = true),
            @Mapping(target = "buyPrice", source = "ruleDto.buyPrice"),
            @Mapping(target = "sellPrice", source = "ruleDto.sellPrice"),
            @Mapping(target = "stock", source = "stock", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL),
            @Mapping(target = "user", source = "user")
    })
    Rule toEntity(RuleDto ruleDto, Stock stock, User user);
}
