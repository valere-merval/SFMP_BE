package com.smartfan.mobility.transport.application;

import com.smartfan.mobility.transport.api.RideRequestCreateRequest;
import com.smartfan.mobility.transport.api.RideRequestStatusUpdateRequest;
import com.smartfan.mobility.transport.api.RideRequestView;
import com.smartfan.mobility.transport.domain.RideRequestEntity;
import com.smartfan.mobility.transport.domain.RideRequestRepository;
import com.smartfan.mobility.transport.domain.RideRequestStatus;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
@Transactional
public class RideRequestService {
    private final RideRequestRepository repository;

    public RideRequestService(RideRequestRepository repository) {
        this.repository = repository;
    }

    public RideRequestView create(RideRequestCreateRequest request) {
        RideRequestEntity entity = new RideRequestEntity(
            UUID.randomUUID(),
            require(request.getMatchId(), "matchId"),
            require(request.getMatchTitle(), "matchTitle"),
            require(request.getPassengerEmail(), "passengerEmail"),
            defaultText(request.getPassengerName(), request.getPassengerEmail()),
            normalizeProviderType(require(request.getProviderType(), "providerType")),
            defaultText(request.getPickupLabel(), "Pickup"),
            request.getPickupLatitude(),
            request.getPickupLongitude(),
            defaultText(request.getDestinationLabel(), "Destination"),
            request.getDestinationLatitude(),
            request.getDestinationLongitude(),
            request.getNote()
        );
        return toView(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<RideRequestView> listPassengerRequests(String passengerEmail) {
        return repository.findByPassengerEmailOrderByCreatedAtDesc(passengerEmail)
            .stream()
            .map(this::toView)
            .toList();
    }

    @Transactional(readOnly = true)
    public RideRequestView getById(UUID id) {
        return toView(load(id));
    }

    @Transactional(readOnly = true)
    public List<RideRequestView> listDriverInbox(String matchId, String providerType) {
        String normalizedProviderType = normalizeProviderType(providerType);
        if (matchId == null || matchId.isBlank()) {
            return List.of();
        }
        return repository.findByMatchIdAndProviderTypeAndStatusOrderByCreatedAtDesc(matchId, normalizedProviderType, RideRequestStatus.PENDING)
            .stream()
            .map(this::toView)
            .toList();
    }

    public RideRequestView accept(UUID id, RideRequestStatusUpdateRequest request) {
        RideRequestEntity entity = load(id);
        if (entity.getStatus() != RideRequestStatus.PENDING) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ride request is no longer pending");
        }
        entity.setStatus(RideRequestStatus.ACCEPTED);
        entity.setDriverProfileId(defaultText(request.getDriverProfileId(), entity.getDriverProfileId()));
        entity.setDriverDisplayName(defaultText(request.getDriverDisplayName(), entity.getDriverDisplayName()));
        entity.setDriverPhone(defaultText(request.getDriverPhone(), entity.getDriverPhone()));
        entity.setResponseNote(request.getResponseNote());
        return toView(repository.save(entity));
    }

    public RideRequestView refuse(UUID id, RideRequestStatusUpdateRequest request) {
        RideRequestEntity entity = load(id);
        if (entity.getStatus() != RideRequestStatus.PENDING) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ride request is no longer pending");
        }
        entity.setStatus(RideRequestStatus.REFUSED);
        entity.setDriverProfileId(defaultText(request.getDriverProfileId(), entity.getDriverProfileId()));
        entity.setDriverDisplayName(defaultText(request.getDriverDisplayName(), entity.getDriverDisplayName()));
        entity.setDriverPhone(defaultText(request.getDriverPhone(), entity.getDriverPhone()));
        entity.setResponseNote(request.getResponseNote());
        return toView(repository.save(entity));
    }

    private RideRequestEntity load(UUID id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ride request not found"));
    }

    private RideRequestView toView(RideRequestEntity entity) {
        return new RideRequestView(
            entity.getId(),
            entity.getMatchId(),
            entity.getMatchTitle(),
            entity.getPassengerEmail(),
            entity.getPassengerName(),
            entity.getProviderType(),
            entity.getPickupLabel(),
            entity.getPickupLatitude(),
            entity.getPickupLongitude(),
            entity.getDestinationLabel(),
            entity.getDestinationLatitude(),
            entity.getDestinationLongitude(),
            entity.getStatus(),
            entity.getDriverProfileId(),
            entity.getDriverDisplayName(),
            entity.getDriverPhone(),
            entity.getResponseNote(),
            entity.getNote(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }

    private String require(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, fieldName + " is required");
        }
        return value.trim();
    }

    private String defaultText(String value, String fallback) {
        if (value == null || value.isBlank()) {
            return fallback;
        }
        return value.trim();
    }

    private String normalizeProviderType(String value) {
        return value.trim().toLowerCase(Locale.ROOT);
    }
}
