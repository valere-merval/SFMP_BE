package com.smartfan.mobility.drivers.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.demo.DemoCatalogue;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/drivers")
public class DriverController {

    @GetMapping
    public ApiResponse<List<DemoCatalogue.DriverView>> list() {
        return ApiResponse.ok(DemoCatalogue.drivers(), "Drivers ready");
    }
}
