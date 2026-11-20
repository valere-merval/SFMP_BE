package com.smartfan.mobility.transport.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "transport_profile_availabilities", schema = "sfmp")
public class TransportProfileAvailabilityEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String profileId;

    @Column(nullable = false)
    private String matchId;

    @Column(nullable = false)
    private boolean available;

    @Column(nullable = false)
    private boolean ready;

    @Column(nullable = false)
    private String status;

    @Column(length = 2048)
    private String notes;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    protected TransportProfileAvailabilityEntity() {
    }

    public TransportProfileAvailabilityEntity(String id, String profileId, String matchId, boolean available, boolean ready, String status, String notes, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.profileId = profileId;
        this.matchId = matchId;
        this.available = available;
        this.ready = ready;
        this.status = status;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() { return id; }
    public String getProfileId() { return profileId; }
    public String getMatchId() { return matchId; }
    public boolean isAvailable() { return available; }
    public boolean isReady() { return ready; }
    public String getStatus() { return status; }
    public String getNotes() { return notes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void update(boolean available, boolean ready, String status, String notes, LocalDateTime updatedAt) {
        this.available = available;
        this.ready = ready;
        this.status = status;
        this.notes = notes;
        this.updatedAt = updatedAt;
    }
}
