package dev.gunho.rule.mapper;

import dev.gunho.rule.dto.RulePayload;
import dev.gunho.rule.entity.Rule;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-31T00:24:07+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class RuleMapperImpl implements RuleMapper {

    @Override
    public RulePayload toDTO(Rule rule) {
        if ( rule == null ) {
            return null;
        }

        double buyPrice = 0.0d;
        double sellPrice = 0.0d;

        if ( rule.getBuyPrice() != null ) {
            buyPrice = rule.getBuyPrice();
        }
        if ( rule.getSellPrice() != null ) {
            sellPrice = rule.getSellPrice();
        }

        String symbol = null;
        String currency = null;
        double averagePrice = 0.0d;
        int quantity = 0;

        RulePayload rulePayload = new RulePayload( symbol, currency, averagePrice, buyPrice, sellPrice, quantity );

        return rulePayload;
    }
}
