package com.smartfan.mobility.catalog.api;

public record EventCatalogCreateRequest(String title, String description, String imageUrl, String sport, String country, String badge, int eventCount) {
}
