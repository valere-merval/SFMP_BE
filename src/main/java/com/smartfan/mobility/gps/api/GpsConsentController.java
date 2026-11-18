package com.smartfan.mobility.gps.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.demo.DemoCatalogue;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/gps")
public class GpsConsentController {

    @GetMapping("/consents")
    public ApiResponse<List<DemoCatalogue.ConsentView>> consents() {
        return ApiResponse.ok(DemoCatalogue.gpsConsents(), "GPS consents ready");
    }
}
