package com.smartfan.mobility.matches.api;

import java.time.LocalDate;

public record MatchCreateRequest(
        String id,
        String catalogId,
        String homeTeam,
        String awayTeam,
        String homeFlag,
        String awayFlag,
        String country,
        String city,
        String venue,
        String stage,
        LocalDate date,
        String time,
        String gate,
        String sector,
        String transitSummary,
        Boolean ticketAvailable,
        String ticketPrice,
        Integer ticketsTotal,
        String imageUrl) {
}
