package dev.gunho.stock.dto;

import dev.gunho.rule.dto.RuleDto;

public record StockPagePayload(
        String symbol,
        String currency,
        Double averagePrice,
        Double buyPrice,
        Double sellPrice,
        Double marketPrice,
        int quantity,
        RuleDto rule
) {

    public StockPagePayload withMarketPrice(Double buyPrice, Double sellPrice, Double newMarketPrice) {
        return new StockPagePayload(
                this.symbol,
                this.currency,
                this.averagePrice,
                buyPrice,
                sellPrice,
                newMarketPrice,
                this.quantity,
                this.rule
        );
    }

}
