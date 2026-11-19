package com.smartfan.mobility.tickets.api;

public record TicketPurchaseResponse(
        String reference,
        String matchId,
        String matchTitle,
        String amount,
        int quantity,
        String status,
        String message) {
}
