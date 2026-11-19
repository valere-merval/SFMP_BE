package com.smartfan.mobility.tickets.api;

public record TicketPurchaseRequest(String matchId, int quantity, String paymentMethod) {
}
