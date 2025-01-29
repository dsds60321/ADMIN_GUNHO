package dev.gunho.global.util;

import org.springframework.context.annotation.Description;

import java.security.SecureRandom;
import java.util.UUID;

@Description("RANDOM ID 생성")
public class IdUtil {

    /**
     * cserf 키 발급
     */
    public static String generateId() {
        return UUID.randomUUID().toString().substring(0,5)  + System.currentTimeMillis();
    }

    /**
     * 인증 코드 6자리 생성
     */
    public static String randomNumber() {
        return IdUtil.randomNumber(6);
    }

    /**
     * 인증 코드 ?자리 생성 생성
     */
    public static String randomNumber(int range) {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder authCode = new StringBuilder();

        for (int i = 0; i < range; i++) {
            int digit = secureRandom.nextInt(10); // 0부터 9까지 난수 생성
            authCode.append(digit);
        }

        return authCode.toString();
    }
}
