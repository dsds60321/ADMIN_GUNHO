package dev.gunho.chat.mapper;

import dev.gunho.chat.dto.ChatPayload;
import dev.gunho.chat.dto.ChatRoomDTO;
import dev.gunho.chat.entity.ChatRoom;
import dev.gunho.global.mapper.BaseMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.data.redis.connection.stream.MapRecord;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface ChatMapper extends BaseMapper<ChatRoom, ChatRoomDTO> {

    ChatMapper INSTANCE = Mappers.getMapper(ChatMapper.class);

    // 단일 ChatRoom -> ChatRoomDTO 변환
    @Override
    @Mappings({
            @Mapping(target = "hostName", source = "host.nick"),
            @Mapping(target = "userCount", expression = "java(chatRoom.getUsers() != null ? chatRoom.getUsers().size() : 0)")
    })
    ChatRoomDTO toDTO(ChatRoom chatRoom);


    // MapRecord -> ChatPayload 변환 메서드
    @Mapping(target = "message", expression = "java((String) mapRecord.getValue().get(\"message\"))")
    @Mapping(target = "regDate", expression = "java((String) mapRecord.getValue().get(\"regDate\"))")
    @Mapping(target = "userId", expression = "java((String) mapRecord.getValue().get(\"userId\"))")
    ChatPayload toPayload(MapRecord<String, Object, Object> mapRecord);

}
