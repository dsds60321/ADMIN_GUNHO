package dev.gunho.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomDTO {
    private long idx;
    private String title;
    private String hostName; // 방장 이름
    private int userCount;   // 참여자 수

}
