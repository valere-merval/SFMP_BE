package com.smartfan.mobility.admin.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.catalogue.FixtureCatalogue;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @GetMapping("/organizations")
    public ApiResponse<List<FixtureCatalogue.OrganizationView>> organizations() {
        return ApiResponse.ok(FixtureCatalogue.organizations(), "Organizations ready");
    }

    @GetMapping("/summary")
    public ApiResponse<List<FixtureCatalogue.MetricView>> summary() {
        return ApiResponse.ok(FixtureCatalogue.adminSummary(), "Admin summary ready");
    }
}
