package dev.gunho.user.constant;

import lombok.Getter;

@Getter
public enum UserRole {

    HOST("HOST"),
    GUEST("GUEST");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

}
