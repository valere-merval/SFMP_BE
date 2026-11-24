package com.smartfan.mobility.telemetry.application;

import java.time.Instant;

public record BehaviorEventMessage(
        String id,
        String userId,
        String eventType,
        String screen,
        String payloadJson,
        String source,
        Instant capturedAt
) {
}
