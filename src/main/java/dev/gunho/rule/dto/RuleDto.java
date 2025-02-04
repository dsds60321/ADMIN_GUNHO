package dev.gunho.rule.dto;

import java.math.BigDecimal;

public record RuleDto(
        String id,
        String symbol,
        int buyPrice,
        BigDecimal buyPercentage,
        int sellPrice,
        BigDecimal sellPercentage) {
}
