package com.smartfan.mobility.gps.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.catalogue.FixtureCatalogue;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/gps")
public class GpsConsentController {

    @GetMapping("/consents")
    public ApiResponse<List<FixtureCatalogue.ConsentView>> consents() {
        return ApiResponse.ok(FixtureCatalogue.gpsConsents(), "GPS consents ready");
    }
}
