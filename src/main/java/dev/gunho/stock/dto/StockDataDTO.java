package dev.gunho.stock.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StockDataDTO {

    private String symbol;
    private StockPriceData stockPriceData;

    @Getter
    @Setter
    public static class StockPriceData {
        private String date;
        private double open;
        private double high;
        private double low;
        private double close;
        private long volume;
    }



}
