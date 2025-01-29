package dev.gunho.user.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EmailPayload {

    private String subject;
    private String to;
    private String from;
    private String bcc;
    private String contents;

}
