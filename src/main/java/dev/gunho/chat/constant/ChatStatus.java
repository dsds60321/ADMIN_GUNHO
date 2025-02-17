package dev.gunho.chat.constant;

public enum ChatStatus {

    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String status;

    ChatStatus(String status) {
        this.status = status;
    }
}