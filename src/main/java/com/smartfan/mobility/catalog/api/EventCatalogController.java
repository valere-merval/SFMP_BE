package com.smartfan.mobility.catalog.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.catalogue.FixtureCatalogue;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/event-catalogs")
public class EventCatalogController {

    @GetMapping
    public ApiResponse<List<FixtureCatalogue.EventCatalogView>> list() {
        return ApiResponse.ok(FixtureCatalogue.eventCatalogs(), "Event catalogs ready");
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<FixtureCatalogue.EventCatalogView> create(@RequestBody EventCatalogCreateRequest request) {
        FixtureCatalogue.EventCatalogView created = new FixtureCatalogue.EventCatalogView(
                request.id(),
                request.title(),
                request.description(),
                request.imageUrl(),
                request.sport(),
                request.country(),
                request.badge(),
                request.eventCount());
        FixtureCatalogue.addEventCatalog(created);
        return ApiResponse.ok(created, "Event catalog created");
    }

    @GetMapping("/{catalogId}/matches")
    public ApiResponse<List<FixtureCatalogue.MatchView>> matches(@PathVariable String catalogId) {
        return ApiResponse.ok(FixtureCatalogue.matchesForCatalog(catalogId), "Catalog matches ready");
    }
}
