package dev.gunho.toooy.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResultStatus {

    private final long wins;
    private final long losses;

    public boolean isWinMoreLoss() {
        return wins > losses;
    }

}
