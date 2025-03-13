package dev.gunho.chat.mapper;

import dev.gunho.chat.dto.ChatPayload;
import dev.gunho.chat.dto.ChatRoomDTO;
import dev.gunho.chat.entity.ChatRoom;
import dev.gunho.user.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-25T23:16:58+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class ChatMapperImpl implements ChatMapper {

    @Override
    public ChatRoomDTO toDTO(ChatRoom chatRoom) {
        if ( chatRoom == null ) {
            return null;
        }

        ChatRoomDTO chatRoomDTO = new ChatRoomDTO();

        chatRoomDTO.setHostName( chatRoomHostNick( chatRoom ) );
        chatRoomDTO.setIdx( chatRoom.getIdx() );
        chatRoomDTO.setTitle( chatRoom.getTitle() );

        chatRoomDTO.setUserCount( chatRoom.getUsers() != null ? chatRoom.getUsers().size() : 0 );

        return chatRoomDTO;
    }

    @Override
    public ChatPayload toPayload(MapRecord<String, Object, Object> mapRecord) {
        if ( mapRecord == null ) {
            return null;
        }

        String message = (String) mapRecord.getValue().get("message");
        String regDate = (String) mapRecord.getValue().get("regDate");
        String userId = (String) mapRecord.getValue().get("userId");

        ChatPayload chatPayload = new ChatPayload( message, regDate, userId );

        return chatPayload;
    }

    private String chatRoomHostNick(ChatRoom chatRoom) {
        if ( chatRoom == null ) {
            return null;
        }
        User host = chatRoom.getHost();
        if ( host == null ) {
            return null;
        }
        String nick = host.getNick();
        if ( nick == null ) {
            return null;
        }
        return nick;
    }
}
