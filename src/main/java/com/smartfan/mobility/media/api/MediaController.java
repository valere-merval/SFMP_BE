package com.smartfan.mobility.media.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.demo.DemoCatalogue;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/media")
public class MediaController {

    @GetMapping("/assets")
    public ApiResponse<List<DemoCatalogue.AssetView>> assets() {
        return ApiResponse.ok(DemoCatalogue.mediaAssets(), "Media assets ready");
    }
}
