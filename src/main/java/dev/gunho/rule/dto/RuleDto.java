package dev.gunho.rule.dto;

public record RuleDto(
        String symbol,
        String currency,
        double average_price,
        double buy_target_price,
        double sell_target_price,
        String quantity,
        String rule
) {
}
