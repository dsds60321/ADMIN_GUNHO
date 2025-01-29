package dev.gunho.global.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public class SessionUtil {

    public static void setCookie(HttpServletResponse response, String name, String value, long expiry) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true); // HTTP-Only로 설정 (JavaScript 접근 불가)
        cookie.setSecure(true); // HTTPS에서만 사용
        cookie.setMaxAge((int) expiry); // 토큰 만료 시간
        cookie.setPath("/"); // 모든 경로에서 쿠키 유효
        response.addCookie(cookie);
    }
}
