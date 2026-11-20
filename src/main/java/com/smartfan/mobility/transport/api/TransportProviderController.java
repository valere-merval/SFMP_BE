package com.smartfan.mobility.transport.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.transport.application.TransportProviderService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transport-providers")
public class TransportProviderController {

    private final TransportProviderService transportProviderService;

    public TransportProviderController(TransportProviderService transportProviderService) {
        this.transportProviderService = transportProviderService;
    }

    @GetMapping
    public ApiResponse<List<com.smartfan.mobility.shared.catalogue.FixtureCatalogue.TransportProviderView>> list(@RequestParam(required = false) String matchId) {
        return ApiResponse.ok(transportProviderService.listPublic(matchId), "Transport providers ready");
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<TransportProviderAdminView> create(@RequestBody TransportProviderCreateRequest request) {
        return ApiResponse.ok(transportProviderService.create(request), "Transport provider registered");
    }
}
