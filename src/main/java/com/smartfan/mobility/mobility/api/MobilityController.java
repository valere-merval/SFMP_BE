package com.smartfan.mobility.mobility.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.demo.DemoCatalogue;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mobility")
public class MobilityController {

    @GetMapping("/options")
    public ApiResponse<List<DemoCatalogue.MobilityOptionView>> options() {
        return ApiResponse.ok(DemoCatalogue.mobilityOptions(), "Mobility options ready");
    }
}
