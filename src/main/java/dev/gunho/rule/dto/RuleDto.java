package dev.gunho.rule.dto;

import java.math.BigDecimal;

public record RuleDto(
        long idx,
        String id,
        String symbol,
        int buyPrice,
        BigDecimal buyPercentage,
        int sellPrice,
        BigDecimal sellPercentage) {
}
