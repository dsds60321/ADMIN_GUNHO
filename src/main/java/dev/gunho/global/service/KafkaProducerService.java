package dev.gunho.global.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.gunho.global.dto.KafkaMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final KafkaResponseService kafkaResponseService; // 응답 처리 서비스

    /**
     * 응답을 기다리지 않고 Kafka로 단순 메시지를 전송합니다.
     *
     * @param topic 전송할 Kafka 토픽
     * @param payload 전달할 데이터
     */
    public <T> void sendMessage(String topic, T payload) {
        String requestId = UUID.randomUUID().toString(); // 고유 요청 ID 생성
        KafkaMessage message = new KafkaMessage(requestId, payload); // 메시지 생성

        try {
            // 요청 메시지를 JSON으로 변환
            String messageJson = objectMapper.writeValueAsString(message);

            // Kafka 요청 토픽으로 메시지 전송
            kafkaTemplate.send(topic, messageJson);
            log.info("Sent message to Kafka topic: {} , Request ID: {}" , topic, requestId);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize Kafka message", e);
        }
    }


    /**
     * Kafka로 요청을 전송하고 응답을 비동기로 받습니다.
     * @param topic 요청할 Kafka 토픽
     * @param payload 전달할 데이터
     * @return CompletableFuture로 응답 반환
     */
    public <T> CompletableFuture<String> sendRequest(String topic, T payload) {
        String requestId = UUID.randomUUID().toString(); // 고유 요청 ID 생성
        KafkaMessage message = new KafkaMessage(requestId, payload); // 요청 생성

        try {
            // 요청 메시지를 JSON으로 변환
            String messageJson = objectMapper.writeValueAsString(message);

            // Kafka 요청 토픽으로 메시지 전송
            kafkaTemplate.send(topic, requestId, messageJson);
            // 응답 Future 생성
            return kafkaResponseService.createFutureForRequest(requestId);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize Kafka message", e);
        }
    }

}
