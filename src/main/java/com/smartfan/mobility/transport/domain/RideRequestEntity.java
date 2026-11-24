package com.smartfan.mobility.transport.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ride_requests")
public class RideRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String matchId;

    @Column(nullable = false)
    private String providerType;

    @Column(nullable = false)
    private String matchTitle;

    @Column(nullable = false)
    private String passengerEmail;

    @Column(nullable = false)
    private String passengerName;

    private String passengerPhone;

    @Column(nullable = false)
    private String pickupLabel;

    private Double pickupLatitude;
    private Double pickupLongitude;

    @Column(nullable = false)
    private String destinationLabel;

    private Double destinationLatitude;
    private Double destinationLongitude;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
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

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

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

    public String getProviderType() {
        return providerType;
    }

    public void setProviderType(String providerType) {
        this.providerType = providerType;
    }

    public String getMatchTitle() {
        return matchTitle;
    }

    public void setMatchTitle(String matchTitle) {
        this.matchTitle = matchTitle;
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
