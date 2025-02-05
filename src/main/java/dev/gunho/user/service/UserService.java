package dev.gunho.user.service;

import dev.gunho.global.dto.ApiResponse;
import dev.gunho.global.dto.ApiResponseCode;
import dev.gunho.global.dto.UserDetail;
import dev.gunho.global.entity.Template;
import dev.gunho.global.exception.GlobalException;
import dev.gunho.global.provider.JwtProvider;
import dev.gunho.global.service.KafkaProducerService;
import dev.gunho.global.util.IdUtil;
import dev.gunho.user.dto.EmailPayload;
import dev.gunho.user.dto.InviteDto;
import dev.gunho.user.dto.UserPayload;
import dev.gunho.user.entity.Invite;
import dev.gunho.user.entity.QInvite;
import dev.gunho.user.entity.User;
import dev.gunho.user.mapper.UserMapper;
import dev.gunho.user.repository.InviteRepository;
import dev.gunho.user.repository.TemplateRepository;
import dev.gunho.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final TemplateRepository templateRepository;
    private final UserRepository userRepository;
    private final InviteRepository inviteRepository;
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

    public ResponseEntity<?> invite(UserDetail userDetail, InviteDto inviteDto) {
        User user = userRepository.findById(userDetail.getId())
                .orElseThrow(() -> new GlobalException(ApiResponseCode.NOT_FOUND));

        Invite alreadyInvited = inviteRepository.findByInviterIdxAndEmail(user.getIdx(), inviteDto.email());
        if (alreadyInvited != null) {
            return ApiResponse.BAD_REQUEST("이미 초대된 이메일입니다.");
        }

        // 초대 유저 구분을 위한 토큰 구현
        String token = IdUtil.generateId();
        String inviteLink = "http://localhost:8080/auth/sign-up?token=" + token;

        Template template = templateRepository.getById("EMAIL_INVITE");
        String userId = user.getUserId();
        String nick = user.getNick();

        String contents = template.getContent().replace("{id}", StringUtils.hasText(nick) ? nick : userId)
                .replace("{url}", inviteLink);

        EmailPayload payload = EmailPayload.builder()
                .to(List.of(inviteDto.email()))
                .subject(template.getSubject())
                .from(template.getFrom())
                .contents(contents)
                .build();

        kafkaProducerService.sendMessage("email-topic", payload);

        Invite invite = Invite.builder()
                .inviter(user)
                .email(inviteDto.email())
                .token(token)
                .build();

        inviteRepository.save(invite);


        return ApiResponse.SUCCESS();
    }
}
