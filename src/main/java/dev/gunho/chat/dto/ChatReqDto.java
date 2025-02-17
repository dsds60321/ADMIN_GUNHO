package dev.gunho.chat.dto;

public record ChatReqDto (
        long userIdx,
        long roomIdx,
        String message,
        String userId
        ){
}
