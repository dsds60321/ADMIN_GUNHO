package dev.gunho.global.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.gunho.global.dto.KafkaMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class KafkaResponseService {

    private final ObjectMapper objectMapper;

    // 요청 ID와 CompletableFuture의 매핑을 저장하기 위한 ConcurrentHashMap
    private final ConcurrentHashMap<String, CompletableFuture<String>> pendingRequests = new ConcurrentHashMap<>();

    /**
     * 요청 ID(requestId)에 해당하는 CompletableFuture를 생성하고 저장합니다.
     *
     * @param requestId 요청 ID
     * @return CompletableFuture
     */
    public CompletableFuture<String> createFutureForRequest(String requestId) {
        CompletableFuture<String> future = new CompletableFuture<>();
        pendingRequests.put(requestId, future);
        return future;
    }

    /**
     * Kafka에서 응답 메시지를 소비합니다. 해당 요청 ID에 매핑된 CompletableFuture를 찾아 완료합니다.
     *
     * @param record Kafka 응답 메시지 값(JSON 형태)
     */
    @KafkaListener(topics = "response-topic", groupId = "response-group")
    public void consumeResponse(String record) {
        try {
            // KafkaMessage 역직렬화
            KafkaMessage response = objectMapper.readValue(record, KafkaMessage.class);
            String requestId = response.getRequestId();
            Object payload = response.getPayload();

            // 요청 ID(requestId)에 매핑된 CompletableFuture 찾기
            CompletableFuture<String> future = pendingRequests.remove(requestId);

            if (future != null) {
                // CompletableFuture를 완료하여 응답 반환
                future.complete(payload.toString());
            } else {
                // 해당 요청 ID가 존재하지 않을 경우 로그 출력
                System.err.println("No matching request found for requestId: " + requestId);
            }
        } catch (Exception e) {
            System.err.println("Failed to process response: " + e.getMessage());
        }
    }

}
