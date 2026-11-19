package com.smartfan.mobility.matches.api;

import com.smartfan.mobility.matches.domain.MatchEntity;
import com.smartfan.mobility.matches.domain.MatchRepository;
import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.catalogue.FixtureCatalogue;
import java.util.List;
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

    public MatchesController(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<FixtureCatalogue.MatchView> create(@RequestBody MatchCreateRequest request) {
        MatchEntity entity = new MatchEntity(
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
                request.status(),
                request.gate(),
                request.sector(),
                request.countdown(),
                request.weather(),
                request.temperature(),
                request.transitSummary(),
                request.ticketPrice(),
                request.imageUrl());
        MatchEntity saved = matchRepository.save(entity);
        return ApiResponse.ok(new FixtureCatalogue.MatchView(
                saved.getId(),
                saved.getCatalogId(),
                saved.getHomeTeam(),
                saved.getAwayTeam(),
                saved.getHomeFlag(),
                saved.getAwayFlag(),
                saved.getCity(),
                saved.getCountry(),
                saved.getVenue(),
                saved.getStage(),
                saved.getDate(),
                saved.getTime(),
                saved.getStatus(),
                saved.getGate(),
                saved.getSector(),
                saved.getCountdown(),
                saved.getWeather(),
                saved.getTemperature(),
                saved.getTransitSummary(),
                saved.getTicketPrice(),
                saved.getImageUrl()), "Match created");
    }

    @GetMapping
    public ApiResponse<List<FixtureCatalogue.MatchView>> list() {
        return ApiResponse.ok(matchRepository.findAllByOrderByDateAscTimeAsc().stream().map(entity -> new FixtureCatalogue.MatchView(entity.getId(), entity.getCatalogId(), entity.getHomeTeam(), entity.getAwayTeam(), entity.getHomeFlag(), entity.getAwayFlag(), entity.getCity(), entity.getCountry(), entity.getVenue(), entity.getStage(), entity.getDate(), entity.getTime(), entity.getStatus(), entity.getGate(), entity.getSector(), entity.getCountdown(), entity.getWeather(), entity.getTemperature(), entity.getTransitSummary(), entity.getTicketPrice(), entity.getImageUrl())).toList(), "Matches ready");
    }
}
