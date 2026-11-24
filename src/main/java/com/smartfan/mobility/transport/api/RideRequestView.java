package com.smartfan.mobility.transport.api;

import com.smartfan.mobility.transport.domain.RideRequestStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class RideRequestView {
    private UUID id;
    private String matchId;
    private String matchTitle;
    private String providerType;
    private String passengerEmail;
    private String passengerName;
    private String passengerPhone;
    private String pickupLabel;
    private Double pickupLatitude;
    private Double pickupLongitude;
    private String destinationLabel;
    private Double destinationLatitude;
    private Double destinationLongitude;
    private RideRequestStatus status;
    private String driverProfileId;
    private String driverDisplayName;
    private String driverPhone;
    private String driverVehiclePlate;
    private Double driverLatitude;
    private Double driverLongitude;
    private Double driverDistanceKm;
    private Integer driverEtaMinutes;
    private Double journeyDistanceKm;
    private Integer journeyEtaMinutes;
    private LocalDateTime pickupReachedAt;
    private LocalDateTime tripStartedAt;
    private LocalDateTime tripCompletedAt;
    private String responseNote;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RideRequestView() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getMatchTitle() {
        return matchTitle;
    }

    public void setMatchTitle(String matchTitle) {
        this.matchTitle = matchTitle;
    }

    public String getProviderType() {
        return providerType;
    }

    public void setProviderType(String providerType) {
        this.providerType = providerType;
    }

    public String getPassengerEmail() {
        return passengerEmail;
    }

    public void setPassengerEmail(String passengerEmail) {
        this.passengerEmail = passengerEmail;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerPhone() {
        return passengerPhone;
    }

    public void setPassengerPhone(String passengerPhone) {
        this.passengerPhone = passengerPhone;
    }

    public String getPickupLabel() {
        return pickupLabel;
    }

    public void setPickupLabel(String pickupLabel) {
        this.pickupLabel = pickupLabel;
    }

    public Double getPickupLatitude() {
        return pickupLatitude;
    }

    public void setPickupLatitude(Double pickupLatitude) {
        this.pickupLatitude = pickupLatitude;
    }

    public Double getPickupLongitude() {
        return pickupLongitude;
    }

    public void setPickupLongitude(Double pickupLongitude) {
        this.pickupLongitude = pickupLongitude;
    }

    public String getDestinationLabel() {
        return destinationLabel;
    }

    public void setDestinationLabel(String destinationLabel) {
        this.destinationLabel = destinationLabel;
    }

    public Double getDestinationLatitude() {
        return destinationLatitude;
    }

    public void setDestinationLatitude(Double destinationLatitude) {
        this.destinationLatitude = destinationLatitude;
    }

    public Double getDestinationLongitude() {
        return destinationLongitude;
    }

    public void setDestinationLongitude(Double destinationLongitude) {
        this.destinationLongitude = destinationLongitude;
    }

    public RideRequestStatus getStatus() {
        return status;
    }

    public void setStatus(RideRequestStatus status) {
        this.status = status;
    }

    public String getDriverProfileId() {
        return driverProfileId;
    }

    public void setDriverProfileId(String driverProfileId) {
        this.driverProfileId = driverProfileId;
    }

    public String getDriverDisplayName() {
        return driverDisplayName;
    }

    public void setDriverDisplayName(String driverDisplayName) {
        this.driverDisplayName = driverDisplayName;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getDriverVehiclePlate() {
        return driverVehiclePlate;
    }

    public void setDriverVehiclePlate(String driverVehiclePlate) {
        this.driverVehiclePlate = driverVehiclePlate;
    }

    public Double getDriverLatitude() {
        return driverLatitude;
    }

    public void setDriverLatitude(Double driverLatitude) {
        this.driverLatitude = driverLatitude;
    }

    public Double getDriverLongitude() {
        return driverLongitude;
    }

    public void setDriverLongitude(Double driverLongitude) {
        this.driverLongitude = driverLongitude;
    }

    public Double getDriverDistanceKm() {
        return driverDistanceKm;
    }

    public void setDriverDistanceKm(Double driverDistanceKm) {
        this.driverDistanceKm = driverDistanceKm;
    }

    public Integer getDriverEtaMinutes() {
        return driverEtaMinutes;
    }

    public void setDriverEtaMinutes(Integer driverEtaMinutes) {
        this.driverEtaMinutes = driverEtaMinutes;
    }

    public Double getJourneyDistanceKm() {
        return journeyDistanceKm;
    }

    public void setJourneyDistanceKm(Double journeyDistanceKm) {
        this.journeyDistanceKm = journeyDistanceKm;
    }

    public Integer getJourneyEtaMinutes() {
        return journeyEtaMinutes;
    }

    public void setJourneyEtaMinutes(Integer journeyEtaMinutes) {
        this.journeyEtaMinutes = journeyEtaMinutes;
    }

    public LocalDateTime getPickupReachedAt() {
        return pickupReachedAt;
    }

    public void setPickupReachedAt(LocalDateTime pickupReachedAt) {
        this.pickupReachedAt = pickupReachedAt;
    }

    public LocalDateTime getTripStartedAt() {
        return tripStartedAt;
    }

    public void setTripStartedAt(LocalDateTime tripStartedAt) {
        this.tripStartedAt = tripStartedAt;
    }

    public LocalDateTime getTripCompletedAt() {
        return tripCompletedAt;
    }

    public void setTripCompletedAt(LocalDateTime tripCompletedAt) {
        this.tripCompletedAt = tripCompletedAt;
    }

    public String getResponseNote() {
        return responseNote;
    }

    public void setResponseNote(String responseNote) {
        this.responseNote = responseNote;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
