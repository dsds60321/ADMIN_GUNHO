package dev.gunho.user.service;

import dev.gunho.global.dto.ApiResponse;
import dev.gunho.global.dto.ApiResponseCode;
import dev.gunho.global.exception.GlobalException;
import dev.gunho.global.provider.JwtProvider;
import dev.gunho.user.dto.UserPayload;
import dev.gunho.user.entity.User;
import dev.gunho.user.mapper.UserMapper;
import dev.gunho.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    // 유저 정보
    public ResponseEntity<?> getUser(Long idx) {
        User user = userRepository.findById(idx)
                .orElseThrow(() -> new GlobalException(ApiResponseCode.NOT_FOUND));

        UserPayload payload = userMapper.toPayload(user);
        return ApiResponse.SUCCESS(payload);
    }
}
