package com.smartfan.mobility.analytics.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.catalogue.FixtureCatalogue;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/analytics")
public class AnalyticsController {

    @GetMapping("/metrics")
    public ApiResponse<List<FixtureCatalogue.AnalyticsView>> metrics() {
        return ApiResponse.ok(FixtureCatalogue.analytics(), "Analytics ready");
    }

    @GetMapping("/advanced")
    public ApiResponse<List<FixtureCatalogue.AnalyticsView>> advanced() {
        return ApiResponse.ok(FixtureCatalogue.advancedAnalytics(), "Advanced analytics ready");
    }
}
