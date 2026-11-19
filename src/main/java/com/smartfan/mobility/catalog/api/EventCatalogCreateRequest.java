package com.smartfan.mobility.catalog.api;

import java.util.List;

public record EventCatalogCreateRequest(String title, String description, String imageUrl, List<String> sports, String country, String badge) {
}
