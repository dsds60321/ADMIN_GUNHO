package dev.gunho.stock.dto;

public record StockDTO(
    String symbol,
    String currency,
    Double averagePrice,
    Double buyPrice,
    Double sellPrice,
    int quantity) {
}
