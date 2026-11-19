package com.smartfan.mobility.catalog.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.catalogue.FixtureCatalogue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/catalog")
public class CatalogController {

    @GetMapping
    public ApiResponse<FixtureCatalogue.CatalogueSnapshot> catalogue() {
        return ApiResponse.ok(FixtureCatalogue.snapshot(), "Catalogue ready");
    }
}
