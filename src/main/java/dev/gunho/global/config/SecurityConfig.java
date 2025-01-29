package dev.gunho.global.config;

import dev.gunho.global.filter.JwtFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {


        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> {
                            request.requestMatchers(
                                    "/css/**",       // CSS 파일
                                    "/font/**",       // CSS 파일
                                    "/icon/**",       // CSS 파일
                                    "/images/**",       // CSS 파일
                                    "/js/**",       // CSS 파일
                                    "/scss/**",       // CSS 파일
                                    "/favicon.ico"   // 파비콘
                            ).permitAll();


                    request.requestMatchers("/auth/**").permitAll()
                            .anyRequest().authenticated();
                })
//                세션 사용하는 방식으로 변경
//                .sessionManagement(sessionManagement -> {
//                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//                })
                .sessionManagement(sessionManagement -> {
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
                })
                .exceptionHandling(exceptionHandling -> {
                    exceptionHandling.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
                })
                .addFilterBefore(this.jwtFilter, UsernamePasswordAuthenticationFilter.class);


        return httpSecurity.build();
    }


    // 로그인 페이지 리다이렉트
    static class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
            response.sendRedirect("/auth/sign-in");
        }
    }
}
