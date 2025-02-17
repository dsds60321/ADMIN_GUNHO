package dev.gunho.chat.listener;

import dev.gunho.global.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatListener {

    private final RedisService redisService;

}
