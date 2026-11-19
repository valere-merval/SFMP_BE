package com.smartfan.mobility.drivers.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.catalogue.FixtureCatalogue;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/drivers")
public class DriverController {

    @GetMapping
    public ApiResponse<List<FixtureCatalogue.DriverView>> list() {
        return ApiResponse.ok(FixtureCatalogue.drivers(), "Drivers ready");
    }
}
