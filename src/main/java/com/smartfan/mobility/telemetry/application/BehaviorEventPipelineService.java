package com.smartfan.mobility.telemetry.application;

import com.smartfan.mobility.telemetry.domain.BehaviorEventEntity;
import com.smartfan.mobility.telemetry.domain.BehaviorEventRepository;
import com.smartfan.mobility.telemetry.mongo.BehaviorEventDocument;
import com.smartfan.mobility.telemetry.mongo.BehaviorEventMongoRepository;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BehaviorEventPipelineService {

    private static final Logger log = LoggerFactory.getLogger(BehaviorEventPipelineService.class);

    private final BehaviorEventRepository jpaRepository;
    private final BehaviorEventMongoRepository mongoRepository;
    private final BehaviorEventPublisher publisher;

    public BehaviorEventPipelineService(
            BehaviorEventRepository jpaRepository,
            BehaviorEventMongoRepository mongoRepository,
            BehaviorEventPublisher publisher) {
        this.jpaRepository = jpaRepository;
        this.mongoRepository = mongoRepository;
        this.publisher = publisher;
    }

    public BehaviorEventEntity record(String userId, String eventType, String screen, Map<String, Object> payload, String source) {
        BehaviorEventMessage message = publisher.record(userId, eventType, screen, payload, source);
        BehaviorEventEntity entity = jpaRepository.save(new BehaviorEventEntity(
                message.id(),
                message.userId(),
                message.eventType(),
                message.screen(),
                message.payloadJson()));
        try {
            mongoRepository.save(BehaviorEventDocument.from(message));
            log.info("Mirrored telemetry event to Mongo: id={} source={} eventType={} screen={}", message.id(), source, message.eventType(), message.screen());
        } catch (Exception ex) {
            log.warn("Mongo mirror failed for telemetry event id={} eventType={} screen={}", message.id(), message.eventType(), message.screen(), ex);
        }
        return entity;
    }

    public List<BehaviorEventEntity> list() {
        return jpaRepository.findAllByOrderByCreatedAtDesc();
    }
}
