package com.smartfan.mobility.transport.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "transport_providers", schema = "sfmp")
public class TransportProviderEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String matchId;

    @Column(nullable = false)
    private String providerType;

    @Column(nullable = false)
    private String displayName;

    @Column(nullable = false)
    private String driverName;

    @Column(nullable = false)
    private String vehicleLabel;

    @Column(nullable = false)
    private String vehiclePlate;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String zone;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private boolean active;

    @Column(length = 2048)
    private String permitUrl;

    @Column(length = 2048)
    private String registrationUrl;

    @Column(length = 2048)
    private String driverLicenseUrl;

    @Column(length = 2048)
    private String vehicleImageUrl;

    @Column(length = 2048)
    private String notes;

    @Column(length = 2048)
    private String publicNotes;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    protected TransportProviderEntity() {
    }

    public TransportProviderEntity(String id, String matchId, String providerType, String displayName, String driverName, String vehicleLabel, String vehiclePlate, String city, String country, String zone, String status, boolean active, String permitUrl, String registrationUrl, String driverLicenseUrl, String vehicleImageUrl, String notes, String publicNotes, LocalDateTime createdAt) {
        this.id = id;
        this.matchId = matchId;
        this.providerType = providerType;
        this.displayName = displayName;
        this.driverName = driverName;
        this.vehicleLabel = vehicleLabel;
        this.vehiclePlate = vehiclePlate;
        this.city = city;
        this.country = country;
        this.zone = zone;
        this.status = status;
        this.active = active;
        this.permitUrl = permitUrl;
        this.registrationUrl = registrationUrl;
        this.driverLicenseUrl = driverLicenseUrl;
        this.vehicleImageUrl = vehicleImageUrl;
        this.notes = notes;
        this.publicNotes = publicNotes;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public String getMatchId() { return matchId; }
    public String getProviderType() { return providerType; }
    public String getDisplayName() { return displayName; }
    public String getDriverName() { return driverName; }
    public String getVehicleLabel() { return vehicleLabel; }
    public String getVehiclePlate() { return vehiclePlate; }
    public String getCity() { return city; }
    public String getCountry() { return country; }
    public String getZone() { return zone; }
    public String getStatus() { return status; }
    public boolean isActive() { return active; }
    public String getPermitUrl() { return permitUrl; }
    public String getRegistrationUrl() { return registrationUrl; }
    public String getDriverLicenseUrl() { return driverLicenseUrl; }
    public String getVehicleImageUrl() { return vehicleImageUrl; }
    public String getNotes() { return notes; }
    public String getPublicNotes() { return publicNotes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
