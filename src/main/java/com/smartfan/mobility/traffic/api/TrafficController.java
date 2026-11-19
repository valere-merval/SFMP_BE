package com.smartfan.mobility.traffic.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.catalogue.FixtureCatalogue;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/traffic")
public class TrafficController {

    @GetMapping("/conditions")
    public ApiResponse<List<FixtureCatalogue.TrafficView>> conditions() {
        return ApiResponse.ok(FixtureCatalogue.traffic(), "Traffic conditions ready");
    }
}
