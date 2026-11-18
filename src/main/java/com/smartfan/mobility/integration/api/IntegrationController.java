package com.smartfan.mobility.integration.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.demo.DemoCatalogue;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/integration")
public class IntegrationController {

    @GetMapping("/status")
    public ApiResponse<List<DemoCatalogue.IntegrationView>> status() {
        return ApiResponse.ok(DemoCatalogue.integrations(), "Integration links ready");
    }
}
