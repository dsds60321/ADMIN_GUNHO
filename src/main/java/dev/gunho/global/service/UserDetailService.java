package dev.gunho.toooy.global.service;

import dev.gunho.toooy.global.dto.ApiResponseCode;
import dev.gunho.toooy.global.dto.TooyUserDetail;
import dev.gunho.toooy.global.exception.TooyException;
import dev.gunho.toooy.user.domain.User;
import dev.gunho.toooy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new TooyException(ApiResponseCode.NOT_FOUND));

        return new TooyUserDetail(user);
    }

    public UserDetails loadUserByIdx(Long idx) throws IllegalArgumentException {
        User user = userRepository.findById(idx).orElseThrow(
                () -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
        return new TooyUserDetail(user);
    }
}
