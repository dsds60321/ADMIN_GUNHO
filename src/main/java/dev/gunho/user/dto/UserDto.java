package dev.gunho.user.dto;

import dev.gunho.user.constant.UserRole;

public record UserDto(
        String userId,
        String password,
        String email,
        String nick,
        String _csrf,
        String emailAuth,
        UserRole role
) {
    // 유효성 검증 메소드 추가 가능
    public UserDto {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("아이디는 필수 입력 값입니다.");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("패스워드는 필수 입력 값입니다.");
        }

    }

    public UserDto setSignUp(String encPassword, UserRole role) {
        return new UserDto(userId, encPassword, email, nick, "", "", role);
    }
}

