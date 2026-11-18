package com.smartfan.mobility.analytics.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.demo.DemoCatalogue;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/analytics")
public class AnalyticsController {

    @GetMapping("/metrics")
    public ApiResponse<List<DemoCatalogue.AnalyticsView>> metrics() {
        return ApiResponse.ok(DemoCatalogue.analytics(), "Analytics ready");
    }

    @GetMapping("/advanced")
    public ApiResponse<List<DemoCatalogue.AnalyticsView>> advanced() {
        return ApiResponse.ok(DemoCatalogue.advancedAnalytics(), "Advanced analytics ready");
    }
}
