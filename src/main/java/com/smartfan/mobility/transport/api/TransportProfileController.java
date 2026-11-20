package com.smartfan.mobility.transport.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.transport.application.TransportProfileService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transport-profiles")
public class TransportProfileController {

    private final TransportProfileService transportProfileService;

    public TransportProfileController(TransportProfileService transportProfileService) {
        this.transportProfileService = transportProfileService;
    }

    @GetMapping("/me")
    public ApiResponse<TransportProfileView> me() {
        return ApiResponse.ok(transportProfileService.me(), "Transport profile loaded");
    }

    @PutMapping("/me")
    public ApiResponse<TransportProfileView> save(@RequestBody TransportProfileRequest request) {
        return ApiResponse.ok(transportProfileService.upsertMe(request), "Transport profile saved");
    }

    @GetMapping("/me/availability")
    public ApiResponse<TransportAvailabilityView> availability(@RequestParam String matchId) {
        return ApiResponse.ok(transportProfileService.getAvailability(matchId), "Transport availability loaded");
    }

    @PostMapping("/me/availability")
    public ApiResponse<TransportAvailabilityView> saveAvailability(@RequestParam String matchId, @RequestBody TransportAvailabilityRequest request) {
        return ApiResponse.ok(transportProfileService.setAvailability(matchId, request), "Transport availability saved");
    }

    @GetMapping("/public")
    public ApiResponse<List<com.smartfan.mobility.shared.catalogue.FixtureCatalogue.TransportProviderView>> listPublic(@RequestParam(required = false) String matchId) {
        return ApiResponse.ok(transportProfileService.listPublic(matchId), "Transport providers ready");
    }

    @GetMapping("/admin")
    public ApiResponse<List<TransportProfileView>> adminList() {
        return ApiResponse.ok(transportProfileService.listProfiles(), "Transport profiles ready");
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/me/availability/clear")
    public void clearAvailability() {
        // reserved endpoint for future UX; intentionally no-op for now.
    }
}
