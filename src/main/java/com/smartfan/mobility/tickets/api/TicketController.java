package com.smartfan.mobility.tickets.api;

import com.smartfan.mobility.matches.domain.MatchRepository;
import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.catalogue.FixtureCatalogue;
import java.util.Locale;
import java.util.UUID;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private final MatchRepository matchRepository;

    public TicketController(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @PostMapping("/purchase")
    public ApiResponse<TicketPurchaseResponse> purchase(@RequestBody TicketPurchaseRequest request) {
        FixtureCatalogue.MatchView match = matchRepository.findById(request.matchId())
                .map(entity -> new FixtureCatalogue.MatchView(entity.getId(), entity.getCatalogId(), entity.getHomeTeam(), entity.getAwayTeam(), entity.getHomeFlag(), entity.getAwayFlag(), entity.getCity(), entity.getCountry(), entity.getVenue(), entity.getStage(), entity.getDate(), entity.getTime(), entity.getStatus(), entity.getGate(), entity.getSector(), entity.getCountdown(), entity.getWeather(), entity.getTemperature(), entity.getTransitSummary(), entity.getTicketPrice(), entity.getImageUrl()))
                .orElseThrow(() -> new IllegalArgumentException("Unknown match"));
        String reference = "TKT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(Locale.ROOT);
        TicketPurchaseResponse response = new TicketPurchaseResponse(
                reference,
                match.id(),
                match.homeTeam() + " vs " + match.awayTeam(),
                match.ticketPrice(),
                request.quantity(),
                "CONFIRMED",
                "Checkout session created for backend payment integration");
        return ApiResponse.ok(response, "Ticket purchase ready");
    }
}
