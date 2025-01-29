package dev.gunho.user.dto;


public record EmailVeriftyDto(
        String email,
        String csrf
){
}
