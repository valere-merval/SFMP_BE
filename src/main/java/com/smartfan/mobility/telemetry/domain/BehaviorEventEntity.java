package com.smartfan.mobility.telemetry.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "behavior_events")
@EntityListeners(AuditingEntityListener.class)
public class BehaviorEventEntity {

    @Id
    private String id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "event_type", nullable = false)
    private String eventType;

    @Column(nullable = false)
    private String screen;

    @Column(name = "payload_json", nullable = false, columnDefinition = "text")
    private String payloadJson;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected BehaviorEventEntity() {}

    public BehaviorEventEntity(String id, String userId, String eventType, String screen, String payloadJson) {
        this.id = id;
        this.userId = userId;
        this.eventType = eventType;
        this.screen = screen;
        this.payloadJson = payloadJson;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getEventType() {
        return eventType;
    }

    public String getScreen() {
        return screen;
    }

    public String getPayloadJson() {
        return payloadJson;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
