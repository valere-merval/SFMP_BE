package com.smartfan.mobility.crowd.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.demo.DemoCatalogue;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/crowd")
public class CrowdController {

    @GetMapping("/metrics")
    public ApiResponse<List<DemoCatalogue.MetricView>> metrics() {
        return ApiResponse.ok(DemoCatalogue.crowdMetrics(), "Crowd metrics ready");
    }

    @GetMapping("/heatmaps")
    public ApiResponse<List<DemoCatalogue.HeatmapView>> heatmaps() {
        return ApiResponse.ok(DemoCatalogue.heatmaps(), "Heatmaps ready");
    }

    @GetMapping("/peaks")
    public ApiResponse<List<DemoCatalogue.DemandPeakView>> peaks() {
        return ApiResponse.ok(DemoCatalogue.demandPeaks(), "Demand peaks ready");
    }
}
