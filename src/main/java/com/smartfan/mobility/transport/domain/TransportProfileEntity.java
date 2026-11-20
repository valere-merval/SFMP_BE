package com.smartfan.mobility.transport.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "transport_profiles", schema = "sfmp")
public class TransportProfileEntity {

    @Id
    private String id;

    @Column(nullable = false, unique = true)
    private String ownerEmail;

    @Column(nullable = false)
    private String ownerName;

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
    private boolean active;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    protected TransportProfileEntity() {
    }

    public TransportProfileEntity(String id, String ownerEmail, String ownerName, String providerType, String displayName, String driverName, String vehicleLabel, String vehiclePlate, String city, String country, String zone, String permitUrl, String registrationUrl, String driverLicenseUrl, String vehicleImageUrl, String notes, String publicNotes, boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.ownerEmail = ownerEmail;
        this.ownerName = ownerName;
        this.providerType = providerType;
        this.displayName = displayName;
        this.driverName = driverName;
        this.vehicleLabel = vehicleLabel;
        this.vehiclePlate = vehiclePlate;
        this.city = city;
        this.country = country;
        this.zone = zone;
        this.permitUrl = permitUrl;
        this.registrationUrl = registrationUrl;
        this.driverLicenseUrl = driverLicenseUrl;
        this.vehicleImageUrl = vehicleImageUrl;
        this.notes = notes;
        this.publicNotes = publicNotes;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() { return id; }
    public String getOwnerEmail() { return ownerEmail; }
    public String getOwnerName() { return ownerName; }
    public String getProviderType() { return providerType; }
    public String getDisplayName() { return displayName; }
    public String getDriverName() { return driverName; }
    public String getVehicleLabel() { return vehicleLabel; }
    public String getVehiclePlate() { return vehiclePlate; }
    public String getCity() { return city; }
    public String getCountry() { return country; }
    public String getZone() { return zone; }
    public String getPermitUrl() { return permitUrl; }
    public String getRegistrationUrl() { return registrationUrl; }
    public String getDriverLicenseUrl() { return driverLicenseUrl; }
    public String getVehicleImageUrl() { return vehicleImageUrl; }
    public String getNotes() { return notes; }
    public String getPublicNotes() { return publicNotes; }
    public boolean isActive() { return active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void update(String ownerName, String providerType, String displayName, String driverName, String vehicleLabel, String vehiclePlate, String city, String country, String zone, String permitUrl, String registrationUrl, String driverLicenseUrl, String vehicleImageUrl, String notes, String publicNotes, boolean active, LocalDateTime updatedAt) {
        this.ownerName = ownerName;
        this.providerType = providerType;
        this.displayName = displayName;
        this.driverName = driverName;
        this.vehicleLabel = vehicleLabel;
        this.vehiclePlate = vehiclePlate;
        this.city = city;
        this.country = country;
        this.zone = zone;
        this.permitUrl = permitUrl;
        this.registrationUrl = registrationUrl;
        this.driverLicenseUrl = driverLicenseUrl;
        this.vehicleImageUrl = vehicleImageUrl;
        this.notes = notes;
        this.publicNotes = publicNotes;
        this.active = active;
        this.updatedAt = updatedAt;
    }
}
