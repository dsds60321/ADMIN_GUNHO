package dev.gunho.stock.dto;

import dev.gunho.rule.entity.Rule;

public record StockPagePayload(
        String symbol,
        String currency,
        Double averagePrice,
        Double buyPrice,
        Double sellPrice,
        Double marketPrice,
        int quantity,
        Rule rule
) {

    public StockPagePayload withMarketPrice(Double newMarketPrice) {
        return new StockPagePayload(
                this.symbol,
                this.currency,
                this.averagePrice,
                this.buyPrice,
                this.sellPrice,
                newMarketPrice,
                this.quantity,
                this.rule
        );
    }

}
