package com.smartfan.mobility.transport.api;

import java.time.LocalDateTime;

public record TransportProviderAdminView(
        String id,
        String matchId,
        String matchTitle,
        String providerType,
        String displayName,
        String driverName,
        String vehicleLabel,
        String vehiclePlate,
        String city,
        String country,
        String zone,
        String status,
        boolean active,
        String permitUrl,
        String registrationUrl,
        String driverLicenseUrl,
        String vehicleImageUrl,
        String notes,
        String publicNotes,
        LocalDateTime createdAt) {
}
