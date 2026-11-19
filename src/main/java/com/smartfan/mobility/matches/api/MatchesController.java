package com.smartfan.mobility.matches.api;

import com.smartfan.mobility.catalog.application.CatalogPersistenceService;
import com.smartfan.mobility.matches.domain.MatchEntity;
import com.smartfan.mobility.matches.domain.MatchRepository;
import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.catalogue.FixtureCatalogue;
import java.util.List;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/matches")
public class MatchesController {

    private final MatchRepository matchRepository;
    private final CatalogPersistenceService catalogPersistenceService;

    public MatchesController(MatchRepository matchRepository, CatalogPersistenceService catalogPersistenceService) {
        this.matchRepository = matchRepository;
        this.catalogPersistenceService = catalogPersistenceService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<FixtureCatalogue.MatchView> create(@RequestBody MatchCreateRequest request) {
        var weather = catalogPersistenceService.createMatch(new FixtureCatalogue.MatchView(
                request.id(),
                request.catalogId(),
                request.homeTeam(),
                request.awayTeam(),
                request.homeFlag(),
                request.awayFlag(),
                request.city(),
                request.country(),
                request.venue(),
                request.stage(),
                request.date(),
                request.time(),
                "Scheduled",
                request.gate(),
                request.sector(),
                countdown(request.date(), request.time()),
                "",
                "",
                request.transitSummary(),
                request.ticketAvailable() != null && request.ticketAvailable(),
                request.ticketPrice(),
                request.ticketsTotal(),
                request.ticketsTotal(),
                request.imageUrl()));
        return ApiResponse.ok(weather, "Match created");
    }

    @GetMapping
    public ApiResponse<List<FixtureCatalogue.MatchView>> list() {
        return ApiResponse.ok(matchRepository.findAllByOrderByDateAscTimeAsc().stream().map(entity -> new FixtureCatalogue.MatchView(entity.getId(), entity.getCatalogId(), entity.getHomeTeam(), entity.getAwayTeam(), entity.getHomeFlag(), entity.getAwayFlag(), entity.getCity(), entity.getCountry(), entity.getVenue(), entity.getStage(), entity.getDate(), entity.getTime(), entity.getStatus(), entity.getGate(), entity.getSector(), entity.getCountdown(), entity.getWeather(), entity.getTemperature(), entity.getTransitSummary(), entity.isTicketAvailable(), entity.getTicketPrice(), entity.getTicketsTotal(), entity.getTicketsRemaining(), entity.getImageUrl())).toList(), "Matches ready");
    }

    private String countdown(java.time.LocalDate date, String time) {
        try {
            LocalTime matchTime = LocalTime.parse(time.length() == 5 ? time + ":00" : time, DateTimeFormatter.ISO_LOCAL_TIME);
            LocalDateTime target = LocalDateTime.of(date, matchTime);
            long seconds = java.time.Duration.between(LocalDateTime.now(), target).getSeconds();
            if (seconds <= 0) {
                return "LIVE";
            }
            long days = seconds / 86400;
            long hours = (seconds % 86400) / 3600;
            long minutes = (seconds % 3600) / 60;
            long remaining = seconds % 60;
            return String.format("%02dd %02dh %02dm %02ds", days, hours, minutes, remaining);
        } catch (Exception ex) {
            return "LIVE";
        }
    }
}
