package com.smartfan.mobility.admin.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.demo.DemoCatalogue;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @GetMapping("/organizations")
    public ApiResponse<List<DemoCatalogue.OrganizationView>> organizations() {
        return ApiResponse.ok(DemoCatalogue.organizations(), "Organizations ready");
    }

    @GetMapping("/summary")
    public ApiResponse<List<DemoCatalogue.MetricView>> summary() {
        return ApiResponse.ok(DemoCatalogue.adminSummary(), "Admin summary ready");
    }
}
