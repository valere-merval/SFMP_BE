package com.smartfan.mobility.catalog.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.catalogue.FixtureCatalogue;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/event-catalogs")
public class EventCatalogController {

    @GetMapping
    public ApiResponse<List<FixtureCatalogue.EventCatalogView>> list() {
        return ApiResponse.ok(FixtureCatalogue.eventCatalogs(), "Event catalogs ready");
    }

    @GetMapping("/{catalogId}/matches")
    public ApiResponse<List<FixtureCatalogue.MatchView>> matches(@PathVariable String catalogId) {
        return ApiResponse.ok(FixtureCatalogue.matchesForCatalog(catalogId), "Catalog matches ready");
    }
}
