package dev.gunho.global.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@Builder
@AllArgsConstructor
public class ApiResponse<T> {

    private int code;
    private String message;
    private T res;
    private boolean result;

    public static <T> ResponseEntity<ApiResponse<T>> SUCCESS() {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .code(ApiResponseCode.SUCCESS.getCode())
                .message(ApiResponseCode.SUCCESS.getMessage())
                .res(null)
                .result(true)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> SUCCESS(T body) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .code(ApiResponseCode.SUCCESS.getCode())
                .message(ApiResponseCode.SUCCESS.getMessage())
                .res(body)
                .result(true)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> SUCCESS(String message) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .code(ApiResponseCode.SUCCESS.getCode())
                .message(message)
                .res(null)
                .result(true)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> SUCCESS(String message, T data) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .code(ApiResponseCode.SUCCESS.getCode())
                .message(message)
                .res(data)
                .result(true)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    public static <T> ResponseEntity<ApiResponse<T>> BAD_REQUEST() {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .code(ApiResponseCode.BAD_REQUEST.getCode())
                .message(ApiResponseCode.BAD_REQUEST.getMessage())
                .res(null)
                .result(false)
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> BAD_REQUEST(String message) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .code(ApiResponseCode.BAD_REQUEST.getCode())
                .message(message)
                .res(null)
                .result(false)
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> BAD_REQUEST(String message, T obj) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .code(ApiResponseCode.BAD_REQUEST.getCode())
                .message(message)
                .res(obj)
                .result(false)
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> FORBIDDEN() {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .code(ApiResponseCode.FORBIDDEN.getCode())
                .message(ApiResponseCode.FORBIDDEN.getMessage())
                .res(null)
                .result(false)
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> EXPIRED_TOKEN() {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .code(ApiResponseCode.EXPIRED_TOKEN.getCode())
                .message(ApiResponseCode.EXPIRED_TOKEN.getMessage())
                .res(null)
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> NOT_FOUND(String message) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .code(ApiResponseCode.NOT_FOUND.getCode())
                .message(message)
                .res(null)
                .result(false)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> SERVER_ERROR(int code, String message) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .code(code)
                .message(message)
                .res(null)
                .result(false)
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> SERVER_ERROR() {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .code(ApiResponseCode.INTERNAL_SERVER_ERROR.getCode())
                .message(ApiResponseCode.INTERNAL_SERVER_ERROR.getMessage())
                .res(null)
                .result(false)
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}