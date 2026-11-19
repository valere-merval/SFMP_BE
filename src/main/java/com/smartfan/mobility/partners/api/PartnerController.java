package com.smartfan.mobility.partners.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.catalogue.FixtureCatalogue;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/partners")
public class PartnerController {

    @GetMapping
    public ApiResponse<List<FixtureCatalogue.PartnerView>> list() {
        return ApiResponse.ok(FixtureCatalogue.partners(), "Partners ready");
    }
}
