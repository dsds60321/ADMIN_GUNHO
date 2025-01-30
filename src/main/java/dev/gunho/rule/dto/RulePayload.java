package dev.gunho.rule.dto;

public record RulePayload(
        String symbol,
        String currency,
        double averagePrice,
        double buyPrice,
        double sellPrice,
        int quantity
) {
}
