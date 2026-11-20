package com.smartfan.mobility.transport.api;

import java.time.LocalDateTime;

public record TransportAvailabilityView(
        String id,
        String profileId,
        String matchId,
        String matchTitle,
        boolean available,
        boolean ready,
        String status,
        String notes,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
