package dev.gunho.rule.dto;

import dev.gunho.stock.entity.Stock;

import java.math.BigDecimal;

public record RulePayload(
        String id,
        Stock stock,
        int buyPrice,
        BigDecimal buyPercentage,
        int sellPrice,
        BigDecimal sellPercentage)  {
}
