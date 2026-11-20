package com.smartfan.mobility.transport.api;

public record TransportProviderCreateRequest(
        String id,
        String matchId,
        String providerType,
        String displayName,
        String driverName,
        String vehicleLabel,
        String vehiclePlate,
        String city,
        String country,
        String zone,
        String status,
        Boolean active,
        String permitUrl,
        String registrationUrl,
        String driverLicenseUrl,
        String vehicleImageUrl,
        String notes,
        String publicNotes) {
}
