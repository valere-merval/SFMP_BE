package com.smartfan.mobility.mobility.api;

import com.smartfan.mobility.mobility.application.MobilityOptionService;
import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.catalogue.FixtureCatalogue;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mobility")
public class MobilityController {

    private final MobilityOptionService mobilityOptionService;

    public MobilityController(MobilityOptionService mobilityOptionService) {
        this.mobilityOptionService = mobilityOptionService;
    }

    @GetMapping("/options")
    public ApiResponse<List<FixtureCatalogue.MobilityOptionView>> options() {
        return ApiResponse.ok(mobilityOptionService.listOptions(), "Mobility options ready");
    }

    @GetMapping("/routes")
    public ApiResponse<List<FixtureCatalogue.RouteAdaptationView>> routes() {
        return ApiResponse.ok(FixtureCatalogue.routeAdaptations(), "Adaptive routes ready");
    }
}
