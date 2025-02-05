package dev.gunho.user.controller;

import dev.gunho.user.dto.EmailVeriftyDto;
import dev.gunho.user.dto.UserDto;
import dev.gunho.user.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Description("로그인")
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/sign-up")
    public ModelAndView signUp(@RequestParam(name = "token", required = false) String token) {
        return new ModelAndView("/pages/sign/sign-up","_csrf", authService.getCsrf())
                .addObject("token", token);
    }

    @PostMapping("/sign-up")
    @ResponseBody
    public ResponseEntity<?> signUp(@RequestBody final UserDto userDto) {
        return authService.signUp(userDto);
    }

    @GetMapping("/sign-in")
    public ModelAndView signIn() {
        return new ModelAndView("/pages/sign/sign-in","_csrf", authService.getCsrf());
    }

    @PostMapping("/sign-in")
    @ResponseBody
    public ResponseEntity<?> signIn(HttpSession session, HttpServletResponse response, @RequestBody final UserDto userDto) {
        return authService.signIn(session, response, userDto);
    }

    @PostMapping("/verify/email")
    @ResponseBody
    public ResponseEntity<?> send(@RequestBody final EmailVeriftyDto emailVeriftyDto) {
        return authService.verifyEmail(emailVeriftyDto);
    }

 }
