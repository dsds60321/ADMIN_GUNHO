package dev.gunho.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatPayload {
    public String message;
    public String regDate;
    public String userId;
}
