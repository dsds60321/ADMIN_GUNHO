package dev.gunho.rule.mapper;

import dev.gunho.rule.dto.RuleDto;
import dev.gunho.rule.dto.RulePayload;
import dev.gunho.rule.entity.Rule;
import dev.gunho.stock.entity.Stock;
import dev.gunho.user.entity.User;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-03T00:05:22+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class RuleMapperImpl implements RuleMapper {

    @Override
    public RulePayload toDTO(Rule arg0) {
        if ( arg0 == null ) {
            return null;
        }

        String id = null;
        Stock stock = null;
        int buyPrice = 0;
        BigDecimal buyPercentage = null;
        int sellPrice = 0;
        BigDecimal sellPercentage = null;

        id = arg0.getId();
        stock = arg0.getStock();
        buyPrice = arg0.getBuyPrice();
        buyPercentage = arg0.getBuyPercentage();
        sellPrice = arg0.getSellPrice();
        sellPercentage = arg0.getSellPercentage();

        RulePayload rulePayload = new RulePayload( id, stock, buyPrice, buyPercentage, sellPrice, sellPercentage );

        return rulePayload;
    }

    @Override
    public RulePayload toDTO(Rule rule, Stock stock) {
        if ( rule == null && stock == null ) {
            return null;
        }

        int buyPrice = 0;
        int sellPrice = 0;
        String id = null;
        Stock stock1 = null;
        BigDecimal buyPercentage = null;
        BigDecimal sellPercentage = null;
        if ( rule != null ) {
            buyPrice = rule.getBuyPrice();
            sellPrice = rule.getSellPrice();
            id = rule.getId();
            stock1 = rule.getStock();
            buyPercentage = rule.getBuyPercentage();
            sellPercentage = rule.getSellPercentage();
        }

        RulePayload rulePayload = new RulePayload( id, stock1, buyPrice, buyPercentage, sellPrice, sellPercentage );

        return rulePayload;
    }

    @Override
    public Rule toEntity(RuleDto ruleDto, Stock stock, User user) {
        if ( ruleDto == null && stock == null && user == null ) {
            return null;
        }

        int buyPrice = 0;
        int sellPrice = 0;
        String id = null;
        BigDecimal buyPercentage = null;
        BigDecimal sellPercentage = null;
        if ( ruleDto != null ) {
            buyPrice = ruleDto.buyPrice();
            sellPrice = ruleDto.sellPrice();
            id = ruleDto.id();
            buyPercentage = ruleDto.buyPercentage();
            sellPercentage = ruleDto.sellPercentage();
        }
        Stock stock1 = null;
        stock1 = stock;
        User user1 = null;
        user1 = user;

        long idx = 0L;

        Rule rule = new Rule( idx, id, buyPrice, buyPercentage, sellPercentage, sellPrice, stock1, user1 );

        return rule;
    }
}
