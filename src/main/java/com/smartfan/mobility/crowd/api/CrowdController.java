package com.smartfan.mobility.crowd.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.catalogue.FixtureCatalogue;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/crowd")
public class CrowdController {

    @GetMapping("/metrics")
    public ApiResponse<List<FixtureCatalogue.MetricView>> metrics() {
        return ApiResponse.ok(FixtureCatalogue.crowdMetrics(), "Crowd metrics ready");
    }

    @GetMapping("/heatmaps")
    public ApiResponse<List<FixtureCatalogue.HeatmapView>> heatmaps() {
        return ApiResponse.ok(FixtureCatalogue.heatmaps(), "Heatmaps ready");
    }

    @GetMapping("/peaks")
    public ApiResponse<List<FixtureCatalogue.DemandPeakView>> peaks() {
        return ApiResponse.ok(FixtureCatalogue.demandPeaks(), "Demand peaks ready");
    }
}
