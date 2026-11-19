package com.smartfan.mobility.catalog.application;

import com.smartfan.mobility.catalog.domain.EventCatalogEntity;
import com.smartfan.mobility.catalog.domain.EventCatalogRepository;
import com.smartfan.mobility.shared.weather.MatchWeatherService;
import com.smartfan.mobility.matches.domain.MatchEntity;
import com.smartfan.mobility.matches.domain.MatchRepository;
import com.smartfan.mobility.shared.catalogue.FixtureCatalogue;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CatalogPersistenceService {

    private final EventCatalogRepository eventCatalogRepository;
    private final MatchRepository matchRepository;
    private final MatchWeatherService matchWeatherService;

    public CatalogPersistenceService(EventCatalogRepository eventCatalogRepository, MatchRepository matchRepository, MatchWeatherService matchWeatherService) {
        this.eventCatalogRepository = eventCatalogRepository;
        this.matchRepository = matchRepository;
        this.matchWeatherService = matchWeatherService;
    }

    @Transactional(readOnly = true)
    public List<FixtureCatalogue.EventCatalogView> listCatalogs() {
        return eventCatalogRepository.findAllByOrderByTitleAsc().stream()
                .map(this::toView)
                .toList();
    }

    @Transactional
    public FixtureCatalogue.EventCatalogView createCatalog(String title, String description, String imageUrl, List<String> sports, String country, String badge) {
        String id = generateCatalogId(title);
        String sport = sports == null || sports.isEmpty() ? "Football" : String.join(", ", sports);
        EventCatalogEntity entity = new EventCatalogEntity(id, title, description, imageUrl, sport, country, badge, 0);
        eventCatalogRepository.save(entity);
        return toView(entity);
    }

    @Transactional(readOnly = true)
    public List<FixtureCatalogue.MatchView> listMatches() {
        return matchRepository.findAllByOrderByDateAscTimeAsc().stream().map(this::toView).toList();
    }

    @Transactional(readOnly = true)
    public List<FixtureCatalogue.MatchView> listMatchesForCatalog(String catalogId) {
        if (catalogId == null || catalogId.isBlank()) {
            return listMatches();
        }
        return matchRepository.findByCatalogIdOrderByDateAscTimeAsc(catalogId).stream().map(this::toView).toList();
    }

    @Transactional(readOnly = true)
    public List<FixtureCatalogue.EventView> listEvents() {
        return listMatches().stream()
                .map(match -> new FixtureCatalogue.EventView(match.id(), match.homeTeam() + " vs " + match.awayTeam(), match.city(), match.venue(), match.date(), match.status(), 0))
                .toList();
    }

    @Transactional
    public FixtureCatalogue.MatchView createMatch(FixtureCatalogue.MatchView match) {
        MatchWeatherService.MatchWeatherSnapshot weather = matchWeatherService.enrich(match.city(), match.country(), match.venue(), match.date(), match.time());
        String weatherValue = match.weather() == null || match.weather().isBlank() ? weather.weather() : match.weather();
        String temperatureValue = match.temperature() == null || match.temperature().isBlank() ? weather.temperature() : match.temperature();
        MatchEntity entity = new MatchEntity(
                match.id(),
                match.catalogId(),
                match.homeTeam(),
                match.awayTeam(),
                match.homeFlag(),
                match.awayFlag(),
                match.city(),
                match.country(),
                match.venue(),
                match.stage(),
                match.date(),
                match.time(),
                match.status(),
                match.gate(),
                match.sector(),
                match.countdown(),
                weatherValue,
                temperatureValue,
                match.transitSummary(),
                match.ticketAvailable(),
                match.ticketPrice(),
                match.ticketsTotal(),
                match.ticketsRemaining(),
                match.imageUrl());
        matchRepository.save(entity);
        return toView(entity);
    }

    @Transactional(readOnly = true)
    public FixtureCatalogue.CatalogueSnapshot snapshot() {
        FixtureCatalogue.CatalogueSnapshot snapshot = FixtureCatalogue.snapshot();
        return new FixtureCatalogue.CatalogueSnapshot(
                listEvents(),
                listCatalogs(),
                listMatches(),
                snapshot.venues(),
                snapshot.mobilityOptions(),
                snapshot.bookings(),
                snapshot.drivers(),
                snapshot.partners(),
                snapshot.crowdMetrics(),
                snapshot.forecasts(),
                snapshot.notifications(),
                snapshot.analytics(),
                snapshot.roadmap(),
                snapshot.heatmaps(),
                snapshot.demandPeaks(),
                snapshot.routeAdaptations(),
                snapshot.contextualMessages(),
                snapshot.advancedAnalytics(),
                snapshot.traffic(),
                snapshot.gpsConsents(),
                snapshot.mlInsights(),
                snapshot.auditEntries(),
                snapshot.mediaAssets(),
                snapshot.organizations(),
                snapshot.users(),
                snapshot.integrations(),
                snapshot.operationsSummary(),
                snapshot.adminSummary());
    }

    private FixtureCatalogue.EventCatalogView toView(EventCatalogEntity entity) {
        return new FixtureCatalogue.EventCatalogView(entity.getId(), entity.getTitle(), entity.getDescription(), entity.getImageUrl(), entity.getSport(), entity.getCountry(), entity.getBadge(), entity.getEventCount());
    }

    private FixtureCatalogue.MatchView toView(MatchEntity entity) {
        return new FixtureCatalogue.MatchView(entity.getId(), entity.getCatalogId(), entity.getHomeTeam(), entity.getAwayTeam(), entity.getHomeFlag(), entity.getAwayFlag(), entity.getCity(), entity.getCountry(), entity.getVenue(), entity.getStage(), entity.getDate(), entity.getTime(), entity.getStatus(), entity.getGate(), entity.getSector(), entity.getCountdown(), entity.getWeather(), entity.getTemperature(), entity.getTransitSummary(), entity.isTicketAvailable(), entity.getTicketPrice(), entity.getTicketsTotal(), entity.getTicketsRemaining(), entity.getImageUrl());
    }

    private String generateCatalogId(String title) {
        String slug = title == null ? "catalog" : title.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", "-").replaceAll("^-+|-+$", "");
        if (slug.isBlank()) {
            slug = "catalog";
        }
        return slug + "-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
