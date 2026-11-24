package com.smartfan.mobility.transport.api;

import com.smartfan.mobility.auth.application.AuthService;
import com.smartfan.mobility.auth.domain.AuthUser;
import com.smartfan.mobility.transport.application.RideRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/ride-requests")
public class RideRequestController {
    private final RideRequestService rideRequestService;
    private final AuthService authService;

    public RideRequestController(RideRequestService rideRequestService, AuthService authService) {
        this.rideRequestService = rideRequestService;
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<RideRequestView> create(@RequestBody RideRequestCreateRequest request) {
        AuthUser currentUser = authService.currentUser();
        if (isBlank(request.getPassengerEmail()) && currentUser != null) {
            request.setPassengerEmail(currentUser.email());
        }
        if (isBlank(request.getPassengerName()) && currentUser != null) {
            request.setPassengerName(displayName(currentUser));
        }
        return ResponseEntity.ok(rideRequestService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<RideRequestView>> listPassengerRequests(@RequestParam(required = false) String passengerEmail) {
        AuthUser currentUser = authService.currentUser();
        String email = !isBlank(passengerEmail) ? passengerEmail : currentUser.email();
        return ResponseEntity.ok(rideRequestService.listForPassenger(email));
    }

    @GetMapping("/me")
    public ResponseEntity<List<RideRequestView>> listPassengerRequestsForCurrentUser() {
        String email = authService.currentUser().email();
        return ResponseEntity.ok(rideRequestService.listForPassenger(email));
    }

    @GetMapping("/driver")
    public ResponseEntity<List<RideRequestView>> listDriverRequests(@RequestParam String matchId, @RequestParam String providerType) {
        return ResponseEntity.ok(rideRequestService.listForDriver(matchId, providerType));
    }

    @GetMapping("/driver/assigned")
    public ResponseEntity<List<RideRequestView>> listAssignedDriverRequests(@RequestParam(required = false) String driverProfileId) {
        String driverId = !isBlank(driverProfileId) ? driverProfileId : authService.currentUser().email();
        return ResponseEntity.ok(rideRequestService.listAssignedForDriver(driverId));
    }

    @PostMapping("/{id}/accept")
    public ResponseEntity<RideRequestView> accept(@PathVariable UUID id, @RequestBody(required = false) RideRequestStatusUpdateRequest request) {
        RideRequestStatusUpdateRequest effectiveRequest = request == null ? new RideRequestStatusUpdateRequest() : request;
        AuthUser currentUser = authService.currentUser();
        if (isBlank(effectiveRequest.getDriverDisplayName()) && currentUser != null) {
            effectiveRequest.setDriverDisplayName(displayName(currentUser));
        }
        if (isBlank(effectiveRequest.getDriverProfileId()) && currentUser != null) {
            effectiveRequest.setDriverProfileId(currentUser.email());
        }
        return ResponseEntity.ok(rideRequestService.accept(id, effectiveRequest));
    }

    @PostMapping("/{id}/refuse")
    public ResponseEntity<RideRequestView> refuse(@PathVariable UUID id, @RequestBody(required = false) RideRequestStatusUpdateRequest request) {
        return ResponseEntity.ok(rideRequestService.refuse(id, request == null ? new RideRequestStatusUpdateRequest() : request));
    }

    @PostMapping("/{id}/arrive")
    public ResponseEntity<RideRequestView> arrive(@PathVariable UUID id) {
        return ResponseEntity.ok(rideRequestService.arrive(id));
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<RideRequestView> startTrip(@PathVariable UUID id) {
        return ResponseEntity.ok(rideRequestService.startTrip(id));
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<RideRequestView> completeTrip(@PathVariable UUID id) {
        return ResponseEntity.ok(rideRequestService.completeTrip(id));
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private String displayName(AuthUser user) {
        return !isBlank(user.fullName()) ? user.fullName() : user.email();
    }
}
