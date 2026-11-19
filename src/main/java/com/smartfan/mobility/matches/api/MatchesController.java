package com.smartfan.mobility.matches.api;

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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<FixtureCatalogue.MatchView> create(@RequestBody MatchCreateRequest request) {
        FixtureCatalogue.MatchView created = new FixtureCatalogue.MatchView(
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
        FixtureCatalogue.addMatch(created);
        return ApiResponse.ok(created, "Match created");
    }

    @GetMapping
    public ApiResponse<List<FixtureCatalogue.MatchView>> list() {
        return ApiResponse.ok(FixtureCatalogue.matches(), "Matches ready");
    }
}
