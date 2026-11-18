package com.smartfan.mobility.intelligence.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.demo.DemoCatalogue;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/intelligence")
public class IntelligenceController {

    @GetMapping("/insights")
    public ApiResponse<List<DemoCatalogue.InsightView>> insights() {
        return ApiResponse.ok(DemoCatalogue.mlInsights(), "ML insights ready");
    }
}
