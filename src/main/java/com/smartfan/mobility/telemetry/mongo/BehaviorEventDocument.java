package com.smartfan.mobility.telemetry.mongo;

import com.smartfan.mobility.telemetry.application.BehaviorEventMessage;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "behavior_events")
public class BehaviorEventDocument {

    @Id
    private String id;
    private String userId;
    private String eventType;
    private String screen;
    private String payloadJson;
    private String source;
    private Instant capturedAt;

    protected BehaviorEventDocument() {
    }

    public BehaviorEventDocument(String id, String userId, String eventType, String screen, String payloadJson, String source, Instant capturedAt) {
        this.id = id;
        this.userId = userId;
        this.eventType = eventType;
        this.screen = screen;
        this.payloadJson = payloadJson;
        this.source = source;
        this.capturedAt = capturedAt;
    }

    public static BehaviorEventDocument from(BehaviorEventMessage message) {
        return new BehaviorEventDocument(
                message.id(),
                message.userId(),
                message.eventType(),
                message.screen(),
                message.payloadJson(),
                message.source(),
                message.capturedAt());
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

    public String getSource() {
        return source;
    }

    public Instant getCapturedAt() {
        return capturedAt;
    }
}
