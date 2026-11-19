package com.smartfan.mobility.tickets.api;

import com.smartfan.mobility.matches.domain.MatchRepository;
import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.catalogue.FixtureCatalogue;
import java.util.Locale;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private final MatchRepository matchRepository;

    public TicketController(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Transactional
    @PostMapping("/purchase")
    public ApiResponse<TicketPurchaseResponse> purchase(@RequestBody TicketPurchaseRequest request) {
        var entity = matchRepository.findById(request.matchId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unknown match"));
        if (!entity.isTicketAvailable() || entity.getTicketsRemaining() == null || entity.getTicketsRemaining() < request.quantity()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tickets are not available");
        }
        int remaining = entity.getTicketsRemaining() - request.quantity();
        entity.updateTicketInventory(entity.getTicketsTotal(), remaining);
        matchRepository.save(entity);
        String reference = "TKT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(Locale.ROOT);
        TicketPurchaseResponse response = new TicketPurchaseResponse(
                reference,
                entity.getId(),
                entity.getHomeTeam() + " vs " + entity.getAwayTeam(),
                entity.getTicketPrice() == null || entity.getTicketPrice().isBlank() ? "TBD" : entity.getTicketPrice(),
                request.quantity(),
                "CONFIRMED",
                "Checkout session created for backend payment integration. " + remaining + " tickets remaining.");
        return ApiResponse.ok(response, "Ticket purchase ready");
    }
}
