package dev.gunho.rule.mapper;

import dev.gunho.global.mapper.BaseMapper;
import dev.gunho.rule.dto.RulePayload;
import dev.gunho.rule.entity.Rule;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface RuleMapper extends BaseMapper<Rule, RulePayload> {

    RuleMapper INSTANCE = Mappers.getMapper(RuleMapper.class);

    @Override
    RulePayload toDTO(Rule rule);

}
