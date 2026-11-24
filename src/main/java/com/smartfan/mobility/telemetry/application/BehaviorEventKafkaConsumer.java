package com.smartfan.mobility.telemetry.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartfan.mobility.telemetry.mongo.BehaviorEventDocument;
import com.smartfan.mobility.telemetry.mongo.BehaviorEventMongoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.telemetry.kafka-consumer-enabled", havingValue = "true")
public class BehaviorEventKafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(BehaviorEventKafkaConsumer.class);

    private final BehaviorEventMongoRepository mongoRepository;
    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
    private final String topic;

    public BehaviorEventKafkaConsumer(
            BehaviorEventMongoRepository mongoRepository,
            @Value("${app.telemetry.topic:sfmp.behavior-events}") String topic) {
        this.mongoRepository = mongoRepository;
        this.topic = topic;
    }

    @KafkaListener(
            topics = "${app.telemetry.topic:sfmp.behavior-events}",
            groupId = "${spring.kafka.consumer.group-id:sfmp-behavior-events}",
            containerFactory = "behaviorEventKafkaListenerContainerFactory")
    public void onMessage(String payload) {
        try {
            BehaviorEventMessage message = objectMapper.readValue(payload, BehaviorEventMessage.class);
            mongoRepository.save(BehaviorEventDocument.from(message));
            log.info("Mirrored telemetry event from Kafka to Mongo: id={} topic={} eventType={} screen={}", message.id(), topic, message.eventType(), message.screen());
        } catch (Exception ex) {
            log.warn("Failed to consume telemetry event from Kafka topic={}", topic, ex);
        }
    }
}
