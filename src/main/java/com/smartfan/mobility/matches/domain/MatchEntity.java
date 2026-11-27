package com.smartfan.mobility.matches.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.time.LocalDate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "matches", schema = "sfmp")
@EntityListeners(AuditingEntityListener.class)
public class MatchEntity {

    @Id
    private String id;

    @Column(name = "catalog_id", nullable = false)
    private String catalogId;

    @Column(name = "home_team", nullable = false)
    private String homeTeam;

    @Column(name = "away_team", nullable = false)
    private String awayTeam;

    @Column(name = "home_flag", nullable = false, columnDefinition = "text")
    private String homeFlag;

    @Column(name = "away_flag", nullable = false, columnDefinition = "text")
    private String awayFlag;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String venue;

    @Column(nullable = false)
    private String stage;

    @Column(name = "match_date", nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String time;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String gate;

    @Column(nullable = false)
    private String sector;

    @Column(nullable = false)
    private String countdown;

    @Column(nullable = false)
    private String weather;

    @Column(nullable = false)
    private String temperature;

    @Column(name = "transit_summary", nullable = false, columnDefinition = "text")
    private String transitSummary;

    @Column(name = "ticket_available", nullable = false)
    private boolean ticketAvailable;

    @Column(name = "ticket_price")
    private String ticketPrice;

    @Column(name = "tickets_total")
    private Integer ticketsTotal;

    @Column(name = "tickets_remaining")
    private Integer ticketsRemaining;

    @Column(name = "image_url", nullable = false, columnDefinition = "text")
    private String imageUrl;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected MatchEntity() {}

    public MatchEntity(String id, String catalogId, String homeTeam, String awayTeam, String homeFlag, String awayFlag, String city, String country, String venue, String stage, LocalDate date, String time, String status, String gate, String sector, String countdown, String weather, String temperature, String transitSummary, boolean ticketAvailable, String ticketPrice, Integer ticketsTotal, Integer ticketsRemaining, String imageUrl) {
        this.id = id;
        this.catalogId = catalogId;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeFlag = homeFlag;
        this.awayFlag = awayFlag;
        this.city = city;
        this.country = country;
        this.venue = venue;
        this.stage = stage;
        this.date = date;
        this.time = time;
        this.status = status;
        this.gate = gate;
        this.sector = sector;
        this.countdown = countdown;
        this.weather = weather;
        this.temperature = temperature;
        this.transitSummary = transitSummary;
        this.ticketAvailable = ticketAvailable;
        this.ticketPrice = ticketPrice;
        this.ticketsTotal = ticketsTotal;
        this.ticketsRemaining = ticketsRemaining;
        this.imageUrl = imageUrl;
    }

    public String getId() { return id; }
    public String getCatalogId() { return catalogId; }
    public String getHomeTeam() { return homeTeam; }
    public String getAwayTeam() { return awayTeam; }
    public String getHomeFlag() { return homeFlag; }
    public String getAwayFlag() { return awayFlag; }
    public String getCity() { return city; }
    public String getCountry() { return country; }
    public String getVenue() { return venue; }
    public String getStage() { return stage; }
    public LocalDate getDate() { return date; }
    public String getTime() { return time; }
    public String getStatus() { return status; }
    public String getGate() { return gate; }
    public String getSector() { return sector; }
    public String getCountdown() { return countdown; }
    public String getWeather() { return weather; }
    public String getTemperature() { return temperature; }
    public String getTransitSummary() { return transitSummary; }
    public boolean isTicketAvailable() { return ticketAvailable; }
    public String getTicketPrice() { return ticketPrice; }
    public Integer getTicketsTotal() { return ticketsTotal; }
    public Integer getTicketsRemaining() { return ticketsRemaining; }
    public String getImageUrl() { return imageUrl; }

    public void updateTicketInventory(Integer ticketsTotal, Integer ticketsRemaining) {
        this.ticketsTotal = ticketsTotal;
        this.ticketsRemaining = ticketsRemaining;
        this.ticketAvailable = ticketsRemaining != null && ticketsRemaining > 0;
    }
}
