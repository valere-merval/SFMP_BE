package com.smartfan.mobility.catalog.api;

import com.smartfan.mobility.catalog.application.CatalogPersistenceService;
import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.catalogue.FixtureCatalogue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/catalog")
public class CatalogController {

    private final CatalogPersistenceService catalogPersistenceService;

    public CatalogController(CatalogPersistenceService catalogPersistenceService) {
        this.catalogPersistenceService = catalogPersistenceService;
    }

    @GetMapping
    public ApiResponse<FixtureCatalogue.CatalogueSnapshot> catalogue() {
        return ApiResponse.ok(catalogPersistenceService.snapshot(), "Catalogue ready");
    }
}
