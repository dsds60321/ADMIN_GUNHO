package dev.gunho.toooy.user.service;

import dev.gunho.toooy.global.dto.ApiResponseCode;
import dev.gunho.toooy.global.exception.TooyException;
import dev.gunho.toooy.user.domain.User;
import dev.gunho.toooy.user.dto.ResultStatus;
import dev.gunho.toooy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public ResultStatus calculateResultStatus(Long idx) {
        User user = userRepository.findById(idx)
                .orElseThrow(() -> new TooyException(ApiResponseCode.NOT_FOUND));

        return user.getCalculateResultStatus();
    }

}
