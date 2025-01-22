package dev.gunho.toooy.global.advice;

import dev.gunho.toooy.global.dto.ApiResponse;
import dev.gunho.toooy.global.dto.ApiResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("잘못된 요청 감지 : {} ", ex.getMessage());
        return ApiResponse.BAD_REQUEST(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handleAllException(Exception ex) {
        log.error("시스템 오류가 발생하였습니다.: {}", ex.getMessage());
        return ApiResponse.SERVER_ERROR(ApiResponseCode.INTERNAL_SERVER_ERROR.getCode(), ex.getMessage());
    }
}
