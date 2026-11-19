package com.smartfan.mobility.media.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.catalogue.FixtureCatalogue;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/media")
public class MediaController {

    @GetMapping("/assets")
    public ApiResponse<List<FixtureCatalogue.AssetView>> assets() {
        return ApiResponse.ok(FixtureCatalogue.mediaAssets(), "Media assets ready");
    }
}
