package com.smartfan.mobility.transport.api;

import java.time.LocalDateTime;

public record TransportProfileView(
        String id,
        String ownerEmail,
        String ownerName,
        String providerType,
        String displayName,
        String driverName,
        String vehicleLabel,
        String vehiclePlate,
        String city,
        String country,
        String zone,
        String permitUrl,
        String registrationUrl,
        String driverLicenseUrl,
        String vehicleImageUrl,
        String notes,
        String publicNotes,
        boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
