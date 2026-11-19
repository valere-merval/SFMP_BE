package com.smartfan.mobility.events.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.catalogue.FixtureCatalogue;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    @GetMapping
    public ApiResponse<List<FixtureCatalogue.EventView>> list() {
        return ApiResponse.ok(FixtureCatalogue.events(), "Events ready");
    }
}
