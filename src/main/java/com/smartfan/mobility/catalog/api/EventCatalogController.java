package com.smartfan.mobility.catalog.api;

import com.smartfan.mobility.catalog.application.CatalogPersistenceService;
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

    private final CatalogPersistenceService catalogPersistenceService;

    public EventCatalogController(CatalogPersistenceService catalogPersistenceService) {
        this.catalogPersistenceService = catalogPersistenceService;
    }

    @GetMapping
    public ApiResponse<List<FixtureCatalogue.EventCatalogView>> list() {
        return ApiResponse.ok(catalogPersistenceService.listCatalogs(), "Event catalogs ready");
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<FixtureCatalogue.EventCatalogView> create(@RequestBody EventCatalogCreateRequest request) {
        FixtureCatalogue.EventCatalogView created = catalogPersistenceService.createCatalog(
                request.title(),
                request.description(),
                request.imageUrl(),
                request.sports(),
                request.country(),
                request.badge());
        return ApiResponse.ok(created, "Event catalog created");
    }

    @GetMapping("/{catalogId}/matches")
    public ApiResponse<List<FixtureCatalogue.MatchView>> matches(@PathVariable String catalogId) {
        return ApiResponse.ok(catalogPersistenceService.listMatchesForCatalog(catalogId), "Catalog matches ready");
    }
}
