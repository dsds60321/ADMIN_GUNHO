package dev.gunho.chat.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.gunho.chat.dto.ChatDto;
import dev.gunho.chat.dto.ChatPayload;
import dev.gunho.chat.dto.ChatReqDto;
import dev.gunho.chat.dto.ChatRoomDTO;
import dev.gunho.chat.entity.ChatRoom;
import dev.gunho.chat.entity.QChatRoom;
import dev.gunho.chat.mapper.ChatMapper;
import dev.gunho.chat.repository.ChatRoomRepository;
import dev.gunho.global.dto.ApiResponse;
import dev.gunho.global.dto.ApiResponseCode;
import dev.gunho.global.dto.PagingDTO;
import dev.gunho.global.dto.UserDetail;
import dev.gunho.global.exception.GlobalException;
import dev.gunho.global.service.KafkaProducerService;
import dev.gunho.global.service.RedisService;
import dev.gunho.global.util.UTIL;
import dev.gunho.user.entity.QUser;
import dev.gunho.user.entity.User;
import dev.gunho.user.mapper.UserMapper;
import dev.gunho.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private static final String CHAT_KEY = "CHAT:%s";

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    private final KafkaProducerService kafkaProducerService;
    private final JPAQueryFactory queryFactory;
    private final RedisService redisService;

    // 채팅방 생성
    @Transactional
    public ResponseEntity<?> createChat(UserDetail userDetail, ChatDto chatDto) {
        User hostUser = userRepository.findById(userDetail.getId())
                .orElseThrow(() -> new GlobalException("유저 정보를 찾을 수 없습니다.", ApiResponseCode.NOT_FOUND.getCode()));

        User friendUser = userRepository.findById(chatDto.friendIdx())
                .orElseThrow(() -> new GlobalException("친구 정보를 찾을 수 없습니다.", ApiResponseCode.NOT_FOUND.getCode()));


        // 새로운 ChatRoom 객체 생성
        ChatRoom chatRoom = ChatRoom.builder()
                .title(chatDto.title())  // 채팅방 이름 설정
                .host(hostUser)          // 방장 설정
                .build();

        // Host 사용자와 Friend 사용자 추가
        chatRoom.addUser(hostUser); // hostUser를 ChatRoom에 추가
        chatRoom.addUser(friendUser); // friendUser를 ChatRoom에 추가

        // ChatRoom 저장
        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);

        log.info("ChatRoom created with ID: {}", savedChatRoom.getIdx());

        return ApiResponse.SUCCESS("채팅방이 성공적으로 생성되었습니다.");

    }


    public ModelAndView getPagingByCondition(ModelAndView mv, PageRequest pageRequest, UserDetail userDetail, String condition) {
        QUser user = QUser.user;
        QChatRoom chatRoom = QChatRoom.chatRoom;

        // 조건에 따라 쿼리 실행 및 데이터 조회
        List<ChatRoom> chatRooms = queryFactory
                .selectFrom(chatRoom)
                .leftJoin(chatRoom.users, user)
//                .where(chatRoomCondition(userIdx, condition))
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();

        // 총 데이터 개수 조회
        long totalElements = queryFactory
                .select(chatRoom.count())
                .from(chatRoom)
                .leftJoin(chatRoom.users, user)
                .where(chatRoomCondition(userDetail.getId(), condition))
                .fetchOne();

        // Page 객체 생성
        Page<ChatRoom> chatRoomPage = new PageImpl<>(chatRooms, pageRequest, totalElements);

        // Mapper를 사용하여 PagingDTO 반환
        PagingDTO<ChatRoomDTO> pagingDTO = ChatMapper.INSTANCE.toPagingDTO(chatRoomPage);
        mv.addObject("PAGE", pagingDTO);
        mv.addObject("userId", userDetail.getUserId());
        return mv;
    }

    // 채팅 요청
    public ResponseEntity<?> sendChat(ChatReqDto chatReqDto) {
        QChatRoom qChatRoom = QChatRoom.chatRoom;
        QUser qUser = QUser.user;

        ChatRoom chatRoom = chatRoomRepository.findByIdx(chatReqDto.roomIdx())
                .orElseThrow(() -> new GlobalException("방 정보를 찾을 수 없습니다.", ApiResponseCode.NOT_FOUND.getCode()));

        String chatRedisKey = String.format(CHAT_KEY, chatReqDto.roomIdx());


        boolean isParticipant = queryFactory
                .selectOne()
                .from(qChatRoom)
                .leftJoin(qChatRoom.users, qUser)
                .where(qChatRoom.idx.eq(chatReqDto.roomIdx())
                        .and(qUser.idx.eq(chatReqDto.userIdx())))
                .fetchFirst() != null;


        if (!isParticipant) {
            return ApiResponse.BAD_REQUEST("대화방에 참여자가 아닙니다.");
        }

        kafkaProducerService.sendMessage("chat-" + chatRoom.getIdx(), chatReqDto);

        List<ChatPayload> chatPayloads = toChayPayloads(chatRedisKey);
        chatPayloads.add(new ChatPayload(chatReqDto.message(), UTIL.getCurrentDate(), chatReqDto.userId()));
        return ApiResponse.SUCCESS(chatPayloads);
    }

    public ResponseEntity<?> recvChat(UserDetail userDetail, ChatReqDto chatReqDto) {
        Long userIdx = userDetail.getId();
        ChatRoom chatRoom = chatRoomRepository.findById(chatReqDto.roomIdx())
                .orElseThrow(() -> new GlobalException("방 정보를 찾을 수 없습니다.", ApiResponseCode.NOT_FOUND.getCode()));


        boolean isParticipant = chatRoom.getUsers().stream().anyMatch(user -> user.getIdx() == userIdx);
        if (!isParticipant) {
            return ApiResponse.BAD_REQUEST("대화방에 참여자가 아닙니다.");
        }

        String chatRedisKey = String.format(CHAT_KEY, chatRoom.getIdx());

        List<ChatPayload> chayPayloads = toChayPayloads(chatRedisKey);
        if (StringUtils.hasText(chatReqDto.message())) {
            chayPayloads.add(new ChatPayload(chatReqDto.message(), UTIL.getCurrentDate(), chatReqDto.userId()));
        }

        return ApiResponse.SUCCESS(chayPayloads);
    }

    private List<ChatPayload> toChayPayloads(String redisKey) {
        List<MapRecord<String, Object, Object>> chats = redisService.readAllFromStream(redisKey);
        return new java.util.ArrayList<>(chats.stream()
                .map(ChatMapper.INSTANCE::toPayload)
                .toList());
    };

    private BooleanExpression chatRoomCondition(Long userIdx, String condition) {
        QUser user = QUser.user;
        QChatRoom chatRoom = QChatRoom.chatRoom;

        return user.idx.eq(userIdx)
                .and(chatRoom.title.containsIgnoreCase(condition));
    }
}
