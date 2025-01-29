package dev.gunho.global.exception;

import dev.gunho.global.dto.ApiResponseCode;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException{
    private final int status;



    public GlobalException(String message, int status) {
        super(message);
        this.status = status;
    }

    public GlobalException(ApiResponseCode responseCode) {
        super(responseCode.getMessage());
        this.status = responseCode.getCode();
    }
}
