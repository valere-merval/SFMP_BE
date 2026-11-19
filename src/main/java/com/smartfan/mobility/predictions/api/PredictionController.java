package com.smartfan.mobility.predictions.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.catalogue.FixtureCatalogue;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/predictions")
public class PredictionController {

    @GetMapping("/forecasts")
    public ApiResponse<List<FixtureCatalogue.ForecastView>> forecasts() {
        return ApiResponse.ok(FixtureCatalogue.forecasts(), "Forecasts ready");
    }

    @GetMapping("/demand")
    public ApiResponse<List<FixtureCatalogue.DemandPeakView>> demand() {
        return ApiResponse.ok(FixtureCatalogue.demandPeaks(), "Demand peaks ready");
    }
}
