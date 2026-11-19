package com.smartfan.mobility.operations.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.catalogue.FixtureCatalogue;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/operations")
public class OperationsController {

    @GetMapping("/summary")
    public ApiResponse<List<FixtureCatalogue.MetricView>> summary() {
        return ApiResponse.ok(FixtureCatalogue.operationsSummary(), "Operations dashboard ready");
    }
}
