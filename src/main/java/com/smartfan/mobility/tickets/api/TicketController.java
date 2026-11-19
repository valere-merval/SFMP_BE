package com.smartfan.mobility.tickets.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.catalogue.FixtureCatalogue;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    @PostMapping("/purchase")
    public ApiResponse<TicketPurchaseResponse> purchase(@RequestBody TicketPurchaseRequest request) {
        FixtureCatalogue.MatchView match = FixtureCatalogue.matches().stream()
                .filter(item -> item.id().equalsIgnoreCase(request.matchId()))
                .findFirst()
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
