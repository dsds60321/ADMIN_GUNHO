package dev.gunho.toooy.user.service;

import dev.gunho.toooy.global.dto.ApiResponse;
import dev.gunho.toooy.global.dto.ApiResponseCode;
import dev.gunho.toooy.global.dto.TooyUserDetail;
import dev.gunho.toooy.global.exception.TooyException;
import dev.gunho.toooy.global.provider.JwtProvider;
import dev.gunho.toooy.user.constant.UserRole;
import dev.gunho.toooy.user.domain.Auth;
import dev.gunho.toooy.user.domain.QAuth;
import dev.gunho.toooy.user.domain.QUser;
import dev.gunho.toooy.user.dto.UserDto;
import dev.gunho.toooy.user.domain.User;
import dev.gunho.toooy.user.mapper.UserMapper;
import dev.gunho.toooy.user.repository.AuthRepository;
import dev.gunho.toooy.user.repository.UserRepository;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    @Description("회원가입")
    @Transactional
    public ResponseEntity<?> signUp(UserDto userDto) {
        String userId = userDto.userId();
        boolean isUser = userRepository.existsByUserId(userId);

        if (isUser) {
            return ApiResponse.BAD_REQUEST("이미 존재하는 계정입니다.");
        }


        User user = UserMapper.INSTANCE.toEntity(userDto.setSignUp(passwordEncoder.encode(userDto.password()), UserRole.DEFAULT));
        User resEntity = userRepository.save(user);

        return resEntity != null ? ApiResponse.SUCCESS("계정 등록에 성공했습니다.") : ApiResponse.SERVER_ERROR();
    }

    @Description("로그인")
    @Transactional
    public ResponseEntity<?> signIn(UserDto userDto) {

        User user = userRepository.findByUserId(userDto.userId())
                .orElseThrow(() -> new TooyException(ApiResponseCode.NOT_FOUND));

        if (!passwordEncoder.matches(userDto.password(), user.getPassword())) {
            return ApiResponse.BAD_REQUEST("패스워드가 일치하지 않습니다.");
        }


        String accessToken = jwtProvider.generateAccessToken(new UsernamePasswordAuthenticationToken(new TooyUserDetail(user), user.getPassword()));
        String refreshToken = jwtProvider.generateRefreshToken(new UsernamePasswordAuthenticationToken(new TooyUserDetail(user), user.getPassword()));

        if (authRepository.existsByUser(user)) {
            Auth auth = user.getAuth();
            auth.updateAccessToken(accessToken);
            auth.updateRefreshToken(refreshToken);
            return ApiResponse.SUCCESS(ApiResponseCode.SUCCESS.getMessage(), auth);
        }

        Auth auth = authRepository.save(Auth.builder()
                .user(user)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build());


        return ApiResponse.SUCCESS(ApiResponseCode.SUCCESS.getMessage(), auth);
    }

    
}
