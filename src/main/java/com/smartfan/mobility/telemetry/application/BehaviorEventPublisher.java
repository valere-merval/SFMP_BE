package com.smartfan.mobility.telemetry.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class BehaviorEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(BehaviorEventPublisher.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
    private final String topic;

    public BehaviorEventPublisher(KafkaTemplate<String, String> kafkaTemplate, Environment environment) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = environment.getProperty("app.telemetry.topic", "sfmp.behavior-events");
    }

    public BehaviorEventMessage record(String userId, String eventType, String screen, Map<String, Object> payload, String source) {
        String id = UUID.randomUUID().toString();
        Instant capturedAt = Instant.now();
        String payloadJson = toJson(payload);
        BehaviorEventMessage message = new BehaviorEventMessage(id, userId, eventType, screen, payloadJson, source, capturedAt);
        try {
            kafkaTemplate.send(topic, id, objectMapper.writeValueAsString(message));
            log.info("Published telemetry event to Kafka: id={} topic={} eventType={} screen={}", id, topic, eventType, screen);
        } catch (Exception ex) {
            log.warn("Kafka publish failed for telemetry event id={} eventType={} screen={}", id, eventType, screen, ex);
        }
        return message;
    }

    private String toJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value == null ? Map.of() : value);
        } catch (JsonProcessingException ex) {
            return "{}";
        }
    }
}
