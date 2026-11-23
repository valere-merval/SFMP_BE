package com.smartfan.mobility.transport.api;

import com.smartfan.mobility.transport.application.RideRequestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/ride-requests")
public class RideRequestController {
    private final RideRequestService service;

    public RideRequestController(RideRequestService service) {
        this.service = service;
    }

    @PostMapping
    public RideRequestView create(@RequestBody RideRequestCreateRequest request) {
        return service.create(request);
    }

    @GetMapping("/me")
    public List<RideRequestView> listPassengerRequests(@RequestParam String passengerEmail) {
        return service.listPassengerRequests(passengerEmail);
    }

    @GetMapping("/driver")
    public List<RideRequestView> listDriverInbox(
        @RequestParam String matchId,
        @RequestParam String providerType
    ) {
        return service.listDriverInbox(matchId, providerType);
    }

    @GetMapping("/{id}")
    public RideRequestView getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping("/{id}/accept")
    public RideRequestView accept(@PathVariable UUID id, @RequestBody RideRequestStatusUpdateRequest request) {
        return service.accept(id, request);
    }

    @PostMapping("/{id}/refuse")
    public RideRequestView refuse(@PathVariable UUID id, @RequestBody RideRequestStatusUpdateRequest request) {
        return service.refuse(id, request);
    }
}
