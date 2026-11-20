package com.smartfan.mobility.transport.api;

public record TransportProfileRequest(
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
        Boolean active) {
}
