package dev.gunho.user.service;

import dev.gunho.global.dto.ApiResponse;
import dev.gunho.global.dto.ApiResponseCode;
import dev.gunho.global.dto.UserDetail;
import dev.gunho.global.entity.Template;
import dev.gunho.global.exception.GlobalException;
import dev.gunho.global.provider.JwtProvider;
import dev.gunho.global.service.KafkaProducerService;
import dev.gunho.user.dto.EmailPayload;
import dev.gunho.user.dto.UserPayload;
import dev.gunho.user.entity.User;
import dev.gunho.user.mapper.UserMapper;
import dev.gunho.user.repository.TemplateRepository;
import dev.gunho.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final TemplateRepository templateRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final KafkaProducerService kafkaProducerService;

    // 유저 정보
    public ResponseEntity<?> getUser(Long idx) {
        User user = userRepository.findById(idx)
                .orElseThrow(() -> new GlobalException(ApiResponseCode.NOT_FOUND));

        UserPayload payload = userMapper.toPayload(user);
        return ApiResponse.SUCCESS(payload);
    }

    public ModelAndView getPagingByCondition(ModelAndView mv, PageRequest pageRequest, Long id, String condition) {
        return null;
    }

    public ResponseEntity<?> invite(UserDetail userDetail, String email) {
        Template template = templateRepository.getById("EMAIL_INVITE");
        String userId = userDetail.getUserId();
        String username = userDetail.getUsername();

        String contents = template.getContent().replace("{id}", StringUtils.hasText(username) ? username : userId)
                .replace("{url}", "http://localhost:8080/user/friends/invite");

        EmailPayload payload = EmailPayload.builder()
                .to("dsds601@naver.com")
                .subject(template.getSubject())
                .from(template.getFrom())
                .contents(contents)
                .build();

        kafkaProducerService.sendMessage("email-topic", payload);
        return ApiResponse.SUCCESS();
    }
}
