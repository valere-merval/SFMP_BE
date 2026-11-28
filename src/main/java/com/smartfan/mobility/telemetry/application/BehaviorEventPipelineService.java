package com.smartfan.mobility.telemetry.application;

import com.smartfan.mobility.telemetry.domain.BehaviorEventEntity;
import com.smartfan.mobility.telemetry.domain.BehaviorEventRepository;
import com.smartfan.mobility.telemetry.mongo.BehaviorEventDocument;
import com.smartfan.mobility.telemetry.mongo.BehaviorEventMongoRepository;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "app.telemetry.enabled", havingValue = "true")
public class BehaviorEventPipelineService {

    private static final Logger log = LoggerFactory.getLogger(BehaviorEventPipelineService.class);

    private final BehaviorEventRepository jpaRepository;
    private final ObjectProvider<BehaviorEventMongoRepository> mongoRepositoryProvider;
    private final BehaviorEventPublisher publisher;
    private final boolean telemetryEnabled;

    public BehaviorEventPipelineService(
            BehaviorEventRepository jpaRepository,
            ObjectProvider<BehaviorEventMongoRepository> mongoRepositoryProvider,
            BehaviorEventPublisher publisher,
            @org.springframework.beans.factory.annotation.Value("${app.telemetry.enabled:true}") boolean telemetryEnabled) {
        this.jpaRepository = jpaRepository;
        this.mongoRepositoryProvider = mongoRepositoryProvider;
        this.publisher = publisher;
        this.telemetryEnabled = telemetryEnabled;
    }

    public BehaviorEventEntity record(String userId, String eventType, String screen, Map<String, Object> payload, String source) {
        BehaviorEventMessage message = telemetryEnabled
                ? publisher.record(userId, eventType, screen, payload, source)
                : new BehaviorEventMessage(
                        java.util.UUID.randomUUID().toString(),
                        userId,
                        eventType,
                        screen,
                        toJson(payload),
                        source,
                        java.time.Instant.now());
        BehaviorEventEntity entity = jpaRepository.save(new BehaviorEventEntity(
                message.id(),
                message.userId(),
                message.eventType(),
                message.screen(),
                message.payloadJson()));
        if (telemetryEnabled) {
            BehaviorEventMongoRepository mongoRepository = mongoRepositoryProvider.getIfAvailable();
            if (mongoRepository != null) {
                try {
                    mongoRepository.save(BehaviorEventDocument.from(message));
                    log.info("Mirrored telemetry event to Mongo: id={} source={} eventType={} screen={}", message.id(), source, message.eventType(), message.screen());
                } catch (Exception ex) {
                    log.warn("Mongo mirror failed for telemetry event id={} eventType={} screen={}", message.id(), message.eventType(), message.screen(), ex);
                }
            } else {
                log.debug("Mongo telemetry repository unavailable, skipping mirror for id={} eventType={} screen={}", message.id(), message.eventType(), message.screen());
            }
        }
        return entity;
    }

    private String toJson(Map<String, Object> payload) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().findAndRegisterModules().writeValueAsString(payload == null ? Map.of() : payload);
        } catch (Exception ex) {
            return "{}";
        }
    }

    public List<BehaviorEventEntity> list() {
        return jpaRepository.findAllByOrderByCreatedAtDesc();
    }
}
