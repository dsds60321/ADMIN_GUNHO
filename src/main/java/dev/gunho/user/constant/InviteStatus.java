package dev.gunho.user.constant;

import lombok.Getter;

@Getter
public enum InviteStatus {

    PENDING("PENDING"),
    ACCEPTED("ACCEPTED"),
    EXPIRED("EXPIRED"),
    REJECTED("REJECTED");

    private final String value;

    InviteStatus(String value) {
        this.value = value;
    }

}
