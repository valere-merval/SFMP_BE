package com.smartfan.mobility.catalog.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.EntityListeners;

@Entity
@Table(name = "event_catalogs", schema = "sfmp")
@EntityListeners(AuditingEntityListener.class)
public class EventCatalogEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @Column(name = "image_url", nullable = false, columnDefinition = "text")
    private String imageUrl;

    @Column(nullable = false)
    private String sport;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String badge;

    @Column(name = "event_count", nullable = false)
    private int eventCount;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected EventCatalogEntity() {}

    public EventCatalogEntity(String id, String title, String description, String imageUrl, String sport, String country, String badge, int eventCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.sport = sport;
        this.country = country;
        this.badge = badge;
        this.eventCount = eventCount;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public String getSport() { return sport; }
    public String getCountry() { return country; }
    public String getBadge() { return badge; }
    public int getEventCount() { return eventCount; }

    public void update(String title, String description, String imageUrl, String sport, String country, String badge, int eventCount) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.sport = sport;
        this.country = country;
        this.badge = badge;
        this.eventCount = eventCount;
    }
}
