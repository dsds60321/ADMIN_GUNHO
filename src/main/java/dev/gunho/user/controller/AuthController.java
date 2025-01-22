package dev.gunho.toooy.user.controller;

import dev.gunho.toooy.global.dto.ApiResponse;
import dev.gunho.toooy.user.dto.UserDto;
import dev.gunho.toooy.user.service.AuthService;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Description("로그인")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody final UserDto userDto) {
        return authService.signUp(userDto);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody final UserDto userDto) {
        return authService.signIn(userDto);
    }

 }
