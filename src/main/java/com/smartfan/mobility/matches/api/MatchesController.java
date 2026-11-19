package com.smartfan.mobility.matches.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.catalogue.FixtureCatalogue;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/matches")
public class MatchesController {

    @GetMapping
    public ApiResponse<List<FixtureCatalogue.MatchView>> list() {
        return ApiResponse.ok(FixtureCatalogue.matches(), "Matches ready");
    }
}
