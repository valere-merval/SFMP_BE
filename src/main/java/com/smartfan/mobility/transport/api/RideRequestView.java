package com.smartfan.mobility.transport.api;

import com.smartfan.mobility.transport.domain.RideRequestStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class RideRequestView {
    private final UUID id;
    private final String matchId;
    private final String matchTitle;
    private final String passengerEmail;
    private final String passengerName;
    private final String providerType;
    private final String pickupLabel;
    private final Double pickupLatitude;
    private final Double pickupLongitude;
    private final String destinationLabel;
    private final Double destinationLatitude;
    private final Double destinationLongitude;
    private final RideRequestStatus status;
    private final String driverProfileId;
    private final String driverDisplayName;
    private final String driverPhone;
    private final String responseNote;
    private final String note;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public RideRequestView(
        UUID id,
        String matchId,
        String matchTitle,
        String passengerEmail,
        String passengerName,
        String providerType,
        String pickupLabel,
        Double pickupLatitude,
        Double pickupLongitude,
        String destinationLabel,
        Double destinationLatitude,
        Double destinationLongitude,
        RideRequestStatus status,
        String driverProfileId,
        String driverDisplayName,
        String driverPhone,
        String responseNote,
        String note,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {
        this.id = id;
        this.matchId = matchId;
        this.matchTitle = matchTitle;
        this.passengerEmail = passengerEmail;
        this.passengerName = passengerName;
        this.providerType = providerType;
        this.pickupLabel = pickupLabel;
        this.pickupLatitude = pickupLatitude;
        this.pickupLongitude = pickupLongitude;
        this.destinationLabel = destinationLabel;
        this.destinationLatitude = destinationLatitude;
        this.destinationLongitude = destinationLongitude;
        this.status = status;
        this.driverProfileId = driverProfileId;
        this.driverDisplayName = driverDisplayName;
        this.driverPhone = driverPhone;
        this.responseNote = responseNote;
        this.note = note;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getMatchId() {
        return matchId;
    }

    public String getMatchTitle() {
        return matchTitle;
    }

    public String getPassengerEmail() {
        return passengerEmail;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public String getProviderType() {
        return providerType;
    }

    public String getPickupLabel() {
        return pickupLabel;
    }

    public Double getPickupLatitude() {
        return pickupLatitude;
    }

    public Double getPickupLongitude() {
        return pickupLongitude;
    }

    public String getDestinationLabel() {
        return destinationLabel;
    }

    public Double getDestinationLatitude() {
        return destinationLatitude;
    }

    public Double getDestinationLongitude() {
        return destinationLongitude;
    }

    public RideRequestStatus getStatus() {
        return status;
    }

    public String getDriverProfileId() {
        return driverProfileId;
    }

    public String getDriverDisplayName() {
        return driverDisplayName;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public String getResponseNote() {
        return responseNote;
    }

    public String getNote() {
        return note;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
