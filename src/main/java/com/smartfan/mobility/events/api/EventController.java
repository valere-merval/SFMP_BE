package com.smartfan.mobility.events.api;

import com.smartfan.mobility.catalog.application.CatalogPersistenceService;
import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.catalogue.FixtureCatalogue;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    private final CatalogPersistenceService catalogPersistenceService;

    public EventController(CatalogPersistenceService catalogPersistenceService) {
        this.catalogPersistenceService = catalogPersistenceService;
    }

    @GetMapping
    public ApiResponse<List<FixtureCatalogue.EventView>> list() {
        return ApiResponse.ok(catalogPersistenceService.snapshot().events(), "Events ready");
    }
}
