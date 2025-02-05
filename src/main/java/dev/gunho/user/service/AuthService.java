package dev.gunho.user.service;

import dev.gunho.global.dto.ApiResponse;
import dev.gunho.global.dto.ApiResponseCode;
import dev.gunho.global.dto.UserDetail;
import dev.gunho.global.entity.Template;
import dev.gunho.global.exception.GlobalException;
import dev.gunho.global.provider.JwtProvider;
import dev.gunho.global.service.KafkaProducerService;
import dev.gunho.global.util.IdUtil;
import dev.gunho.global.util.SessionUtil;
import dev.gunho.user.constant.InviteStatus;
import dev.gunho.user.constant.UserRole;
import dev.gunho.user.entity.Auth;
import dev.gunho.user.entity.Invite;
import dev.gunho.user.entity.User;
import dev.gunho.user.dto.EmailPayload;
import dev.gunho.user.dto.EmailVeriftyDto;
import dev.gunho.user.dto.UserDto;
import dev.gunho.user.mapper.UserMapper;
import dev.gunho.user.repository.AuthRepository;
import dev.gunho.user.repository.InviteRepository;
import dev.gunho.user.repository.TemplateRepository;
import dev.gunho.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private static final String CSRF_KEY = "SESSION|CSRF|%s";
    private static final String EMAIL_VERIFY_KEY = "SESSION|EMAIL|%s";

    private final TemplateRepository templateRepository;
    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final InviteRepository inviteRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;
    private final StringRedisTemplate stringRedisTemplate;
    private final KafkaProducerService kafkaProducerService;

    public String getCsrf() {
        // CSRF 키
        String csrf = IdUtil.generateId();
        String redisKey = String.format(CSRF_KEY, csrf);
        // 이메일 인증에 사용할 코드
        stringRedisTemplate.opsForValue().set(redisKey, IdUtil.randomNumber(), Duration.ofMinutes(30));
        return csrf;
    }

    @Description("회원가입")
    @Transactional
    public ResponseEntity<?> signUp(UserDto userDto) {
        boolean isInvited = StringUtils.hasText(userDto._token());
        String userId = userDto.userId();
        boolean isUser = userRepository.existsByUserId(userId);

        if (isUser) {
            return ApiResponse.BAD_REQUEST("이미 존재하는 계정입니다.");
        }

        // CSRF 값에 이메일 인증 값들어있음 값 확인
        String redisKey = String.format(CSRF_KEY, userDto._csrf());
        String emailAuth = stringRedisTemplate.opsForValue().get(redisKey);

        if (emailAuth == null) {
            return ApiResponse.BAD_REQUEST("만료된 인증코드입니다. 재 발송하여 다시 입력해주세요.");
        }

        if (!emailAuth.equals(userDto.emailAuth())) {
            return ApiResponse.BAD_REQUEST("이메일 인증코드가 일치하지 않습니다.");
        }


        // 초대 받은 경우 GUEST 권한 ADMIN 접근 불가
        User user = UserMapper.INSTANCE.toEntity(userDto.setSignUp(passwordEncoder.encode(userDto.password()), isInvited ? UserRole.GUEST : UserRole.HOST));
        User resEntity = userRepository.save(user);

        // token이 있는 경우 친구초대
        if (isInvited) {
            log.info("친구 초대 유저");
            Invite invite = inviteRepository.findByToken(userDto._token())
                    .orElseThrow(() -> new GlobalException(ApiResponseCode.NOT_FOUND));


            // 초대받은 대상에 UserEntity , 상태 업데이트
            invite.updateInvite(resEntity, InviteStatus.ACCEPTED);
            inviteRepository.save(invite);
        }

        return resEntity != null ? ApiResponse.SUCCESS("계정 등록에 성공했습니다.") : ApiResponse.SERVER_ERROR();
    }

    @Description("로그인")
    @Transactional
    public ResponseEntity<?> signIn(HttpSession session, HttpServletResponse response, UserDto userDto) {

        User user = userRepository.findByUserId(userDto.userId())
                .orElseThrow(() -> new GlobalException(ApiResponseCode.NOT_FOUND));

        if (user.getRole().equals(UserRole.GUEST)) {
            return ApiResponse.BAD_REQUEST("접근 권한이 없습니다.");
        }

        if (!passwordEncoder.matches(userDto.password(), user.getPassword())) {
            return ApiResponse.BAD_REQUEST("패스워드가 일치하지 않습니다.");
        }


        String accessToken = jwtProvider.generateAccessToken(new UsernamePasswordAuthenticationToken(new UserDetail(user), user.getPassword()));
        String refreshToken = jwtProvider.generateRefreshToken(new UsernamePasswordAuthenticationToken(new UserDetail(user), user.getPassword()));


        // AccessToken을 HTTP-Only 쿠키로 설정
        SessionUtil.setCookie(response, "accessToken", accessToken, jwtProvider.getExpireFromToken(accessToken) / 1000);
        // RefreshToken을 HTTP-Only 쿠키로 설정
        SessionUtil.setCookie(response, "refreshToken", refreshToken, jwtProvider.getExpireFromToken(refreshToken) / 1000);

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


    public ResponseEntity<?> verifyEmail(EmailVeriftyDto emailVeriftyDto) {
        Template template = templateRepository.getById("EMAIL_VERIFY");

        // CSRF 인증 값이 담긴 키
        String csrfRedisKey = String.format(CSRF_KEY, emailVeriftyDto.csrf());
        // email 전송 상태에 따른 레디스
        String emailAuthRedisKey = String.format(EMAIL_VERIFY_KEY, emailVeriftyDto.csrf());

        String emailAuthValue = stringRedisTemplate.opsForValue().get(emailAuthRedisKey);

        if (StringUtils.hasText(emailAuthValue)) {
            return ApiResponse.BAD_REQUEST("인증 요청이 발송된 메일입니다. 5분 후 재발송 가능합니다.");
        }


        String authNo = stringRedisTemplate.opsForValue().get(csrfRedisKey);
        // 메일 중복 발송 막기 위해
        stringRedisTemplate.opsForValue().set(emailAuthRedisKey, "SEND", Duration.ofMinutes(5));


        if (!StringUtils.hasText(authNo)) {
            return ApiResponse.BAD_REQUEST("유효시간이 만료되었습니다.");
        }

        String contents = String.format(template.getContent(), authNo);
        EmailPayload payload = EmailPayload.builder()
                .to(List.of(emailVeriftyDto.email()))
                .subject(template.getSubject())
                .from(template.getFrom())
                .contents(contents)
                .build();

        kafkaProducerService.sendMessage("email-topic", payload);
        return ApiResponse.SUCCESS();
    }
}
