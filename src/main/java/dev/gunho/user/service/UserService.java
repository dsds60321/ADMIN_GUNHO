package dev.gunho.user.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.gunho.global.dto.ApiResponse;
import dev.gunho.global.dto.ApiResponseCode;
import dev.gunho.global.dto.PagingDTO;
import dev.gunho.global.dto.UserDetail;
import dev.gunho.global.entity.Template;
import dev.gunho.global.exception.GlobalException;
import dev.gunho.global.service.KafkaProducerService;
import dev.gunho.global.util.IdUtil;
import dev.gunho.user.dto.EmailPayload;
import dev.gunho.user.dto.InviteDto;
import dev.gunho.user.dto.UserPayload;
import dev.gunho.user.entity.Invite;
import dev.gunho.user.entity.QInvite;
import dev.gunho.user.entity.QUser;
import dev.gunho.user.entity.User;
import dev.gunho.user.mapper.InviteMapper;
import dev.gunho.user.mapper.UserMapper;
import dev.gunho.user.repository.InviteRepository;
import dev.gunho.user.repository.TemplateRepository;
import dev.gunho.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final TemplateRepository templateRepository;
    private final UserRepository userRepository;
    private final InviteRepository inviteRepository;
    private final UserMapper userMapper;

    private final JPAQueryFactory queryFactory;
    private final KafkaProducerService kafkaProducerService;

    // 유저 정보
    public ResponseEntity<?> getUser(Long idx) {
        User user = userRepository.findById(idx)
                .orElseThrow(() -> new GlobalException(ApiResponseCode.NOT_FOUND));

        UserPayload payload = userMapper.toPayload(user);
        return ApiResponse.SUCCESS(payload);
    }

    @Transactional
    public ModelAndView getPagingByCondition(ModelAndView mv, PageRequest pageRequest, Long userIdx, String condition) {
        QInvite qInvite = QInvite.invite;
        QUser qUserInviter = QUser.user;
        QUser qUserInvitee = new QUser("invitee");


        // 조건 처리

        List<Invite> invites = queryFactory.selectFrom(qInvite)
                .join(qInvite.inviter, qUserInviter).fetchJoin()  // inviter 즉시 로딩
                .leftJoin(qInvite.invitee, qUserInvitee).fetchJoin()  // invitee 즉시 로딩 (nullable)
                .where(qInvite.inviter.idx.eq(userIdx))  // 동적 조건
                .orderBy(qInvite.invitationTime.desc())  // 최신 초대 정렬
                .offset(pageRequest.getOffset())  // 현재 페이지의 시작 인덱스
                .limit(pageRequest.getPageSize()) // 페이지 크기
                .fetch();  // 결과 가져오기

        // 총 데이터 개수 계산
        long totalElements = queryFactory.select(qInvite.count())
                .from(qInvite)
                .where(qInvite.inviter.idx.eq(userIdx))
                .fetchOne();

        // 전체 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalElements / pageRequest.getPageSize());

        // PagingDTO 생성
        PagingDTO<Invite> pagingDTO = new PagingDTO<>(invites, pageRequest.getPageSize(), totalElements, pageRequest.getPageNumber(), totalPages);


        // ModelAndView에 데이터 추가
        mv.addObject("PAGE", pagingDTO);
        return mv;
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

    public ResponseEntity<?> getFriends(UserDetail userDetail) {

        User user = userRepository.findById(userDetail.getId())
                .orElseThrow(() -> new GlobalException(ApiResponseCode.NOT_FOUND));

        List<InviteDto> friends = user.getSentInvites()
                .stream().map(InviteMapper.INSTANCE::toDto)
                .toList();

        return ApiResponse.SUCCESS(friends);
    }
}
