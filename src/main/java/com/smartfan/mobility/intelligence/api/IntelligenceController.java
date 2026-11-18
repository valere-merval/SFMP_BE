package com.smartfan.mobility.intelligence.api;

import com.smartfan.mobility.intelligence.application.IntelligenceService;
import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.demo.DemoCatalogue;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/intelligence")
public class IntelligenceController {

    private final IntelligenceService intelligenceService;

    public IntelligenceController(IntelligenceService intelligenceService) {
        this.intelligenceService = intelligenceService;
    }

    @GetMapping("/insights")
    public ApiResponse<List<DemoCatalogue.InsightView>> insights() {
        return ApiResponse.ok(DemoCatalogue.mlInsights(), "ML insights ready");
    }

    @PostMapping("/analyze")
    public ApiResponse<AnalyzeResponse> analyze(@RequestBody AnalyzeRequest request) {
        return ApiResponse.ok(intelligenceService.analyze(request), "AI analysis ready");
    }
}
