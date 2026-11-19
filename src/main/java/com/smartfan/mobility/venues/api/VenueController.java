package com.smartfan.mobility.venues.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.catalogue.FixtureCatalogue;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/venues")
public class VenueController {

    @GetMapping
    public ApiResponse<List<FixtureCatalogue.VenueView>> list() {
        return ApiResponse.ok(FixtureCatalogue.venues(), "Venues ready");
    }
}
