package dev.gunho.toooy.global.dto;

import lombok.Getter;

@Getter
public enum ApiResponseCode {
    SUCCESS(200, "요청이 성공적으로 처리되었습니다."),
    NOT_FOUND(404, "요청한 리소스를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(500, "서버 오류가 발생했습니다."),
    BAD_REQUEST(400, "잘못된 요청입니다."),
    FORBIDDEN(403, "권한이 없습니다."),
    EXPIRED_TOKEN(401, "인증되지 않은 토큰입니다.");

    private final int code;
    private final String message;

    ApiResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
