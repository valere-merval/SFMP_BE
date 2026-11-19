package com.smartfan.mobility.telemetry.api;

import java.util.Map;

public record BehaviorEventRequest(String userId, String eventType, String screen, Map<String, Object> payload) {
}
