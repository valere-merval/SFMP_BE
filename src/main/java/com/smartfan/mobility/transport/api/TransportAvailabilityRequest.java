package com.smartfan.mobility.transport.api;

public record TransportAvailabilityRequest(Boolean available, Boolean ready, String status, String notes) {
}
