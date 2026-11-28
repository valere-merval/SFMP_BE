package com.smartfan.mobility.mobility.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "mobility_options", schema = "sfmp")
public class MobilityOptionEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String mode;

    @Column(nullable = false)
    private String duration;

    @Column(nullable = false)
    private String price;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String highlight;

    @Column(nullable = false, length = 2048)
    private String stationAddress;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Column(nullable = false)
    private String assetType;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    protected MobilityOptionEntity() {
    }

    public MobilityOptionEntity(String id, String mode, String duration, String price, String status, String highlight, String stationAddress, Double latitude, Double longitude, String assetType, LocalDateTime createdAt) {
        this.id = id;
        this.mode = mode;
        this.duration = duration;
        this.price = price;
        this.status = status;
        this.highlight = highlight;
        this.stationAddress = stationAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.assetType = assetType;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public String getMode() { return mode; }
    public String getDuration() { return duration; }
    public String getPrice() { return price; }
    public String getStatus() { return status; }
    public String getHighlight() { return highlight; }
    public String getStationAddress() { return stationAddress; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public String getAssetType() { return assetType; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
