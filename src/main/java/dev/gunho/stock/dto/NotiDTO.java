package dev.gunho.stock.dto;

import jakarta.validation.constraints.NotBlank;

public record NotiDTO(
        @NotBlank(message = "노티 상태는 필수 값입니다.")
        boolean isBuy,
        @NotBlank(message = "심볼은 필수 값입니다.")
        String symbol
) {
}
