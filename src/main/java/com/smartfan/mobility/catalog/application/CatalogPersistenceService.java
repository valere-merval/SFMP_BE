package com.smartfan.mobility.catalog.application;

import com.smartfan.mobility.catalog.domain.EventCatalogEntity;
import com.smartfan.mobility.catalog.domain.EventCatalogRepository;
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

    public CatalogPersistenceService(EventCatalogRepository eventCatalogRepository, MatchRepository matchRepository) {
        this.eventCatalogRepository = eventCatalogRepository;
        this.matchRepository = matchRepository;
    }

    @Transactional(readOnly = true)
    public List<FixtureCatalogue.EventCatalogView> listCatalogs() {
        return eventCatalogRepository.findAllByOrderByTitleAsc().stream()
                .map(this::toView)
                .toList();
    }

    @Transactional
    public FixtureCatalogue.EventCatalogView createCatalog(String title, String description, String imageUrl, String sport, String country, String badge, int eventCount) {
        String id = generateCatalogId(title);
        EventCatalogEntity entity = new EventCatalogEntity(id, title, description, imageUrl, sport, country, badge, eventCount);
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
                match.weather(),
                match.temperature(),
                match.transitSummary(),
                match.ticketPrice(),
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
        return new FixtureCatalogue.MatchView(entity.getId(), entity.getCatalogId(), entity.getHomeTeam(), entity.getAwayTeam(), entity.getHomeFlag(), entity.getAwayFlag(), entity.getCity(), entity.getCountry(), entity.getVenue(), entity.getStage(), entity.getDate(), entity.getTime(), entity.getStatus(), entity.getGate(), entity.getSector(), entity.getCountdown(), entity.getWeather(), entity.getTemperature(), entity.getTransitSummary(), entity.getTicketPrice(), entity.getImageUrl());
    }

    private String generateCatalogId(String title) {
        String slug = title == null ? "catalog" : title.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", "-").replaceAll("^-+|-+$", "");
        if (slug.isBlank()) {
            slug = "catalog";
        }
        return slug + "-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
