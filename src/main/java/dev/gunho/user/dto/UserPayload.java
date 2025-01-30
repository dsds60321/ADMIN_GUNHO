package dev.gunho.user.dto;

public record UserPayload (
        String userId,
        String email,
        String nick,
        String role
){
}
