package com.smartfan.mobility.predictions.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.demo.DemoCatalogue;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/predictions")
public class PredictionController {

    @GetMapping("/forecasts")
    public ApiResponse<List<DemoCatalogue.ForecastView>> forecasts() {
        return ApiResponse.ok(DemoCatalogue.forecasts(), "Forecasts ready");
    }
}
