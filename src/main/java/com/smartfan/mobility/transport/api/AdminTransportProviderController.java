package com.smartfan.mobility.transport.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.transport.application.TransportProviderService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/transport-providers")
public class AdminTransportProviderController {

    private final TransportProviderService transportProviderService;

    public AdminTransportProviderController(TransportProviderService transportProviderService) {
        this.transportProviderService = transportProviderService;
    }

    @GetMapping
    public ApiResponse<List<TransportProviderAdminView>> list(@RequestParam(required = false) String matchId) {
        return ApiResponse.ok(transportProviderService.listAdmin(matchId), "Admin transport providers ready");
    }
}
