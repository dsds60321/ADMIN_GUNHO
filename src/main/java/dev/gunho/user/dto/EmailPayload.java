package dev.gunho.user.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class EmailPayload {

    private String subject;
    private List<String> to;
    private String from;
    private String bcc;
    private String contents;

}
