package com.smartfan.mobility.matches.api;

import java.time.LocalDate;

public record MatchCreateRequest(
        String id,
        String catalogId,
        String homeTeam,
        String awayTeam,
        String homeFlag,
        String awayFlag,
        String city,
        String country,
        String venue,
        String stage,
        LocalDate date,
        String time,
        String status,
        String gate,
        String sector,
        String countdown,
        String weather,
        String temperature,
        String transitSummary,
        String ticketPrice,
        String imageUrl) {
}
