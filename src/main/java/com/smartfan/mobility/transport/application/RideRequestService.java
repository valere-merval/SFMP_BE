package com.smartfan.mobility.transport.application;

import com.smartfan.mobility.transport.api.RideRequestCreateRequest;
import com.smartfan.mobility.transport.api.RideRequestStatusUpdateRequest;
import com.smartfan.mobility.transport.api.RideRequestView;
import com.smartfan.mobility.transport.application.TransportProfileService;
import com.smartfan.mobility.transport.domain.RideRequestEntity;
import com.smartfan.mobility.transport.domain.RideRequestRepository;
import com.smartfan.mobility.transport.domain.RideRequestStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
@Transactional
public class RideRequestService {
    private static final double DEFAULT_DRIVER_OFFSET_DEGREES = 0.012;
    private final RideRequestRepository repository;
    private final TransportProfileService transportProfileService;

    public RideRequestService(RideRequestRepository repository, TransportProfileService transportProfileService) {
        this.repository = repository;
        this.transportProfileService = transportProfileService;
    }

    public RideRequestView create(RideRequestCreateRequest request) {
        RideRequestEntity entity = new RideRequestEntity();
        entity.setMatchId(request.getMatchId());
        entity.setProviderType(request.getProviderType());
        entity.setMatchTitle(request.getMatchTitle());
        entity.setPassengerEmail(request.getPassengerEmail());
        entity.setPassengerName(request.getPassengerName());
        entity.setPickupLabel(request.getPickupLabel());
        entity.setPickupLatitude(request.getPickupLatitude());
        entity.setPickupLongitude(request.getPickupLongitude());
        entity.setDestinationLabel(request.getDestinationLabel());
        entity.setDestinationLatitude(request.getDestinationLatitude());
        entity.setDestinationLongitude(request.getDestinationLongitude());
        entity.setStatus(RideRequestStatus.PENDING);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(entity.getCreatedAt());
        return toView(repository.save(entity));
    }

    public List<RideRequestView> listForPassenger(String passengerEmail) {
        if (passengerEmail == null || passengerEmail.isBlank()) {
            return List.of();
        }
        return repository.findByPassengerEmailOrderByCreatedAtDesc(passengerEmail).stream().map(this::toView).toList();
    }

    public List<RideRequestView> listForDriver(String matchId, String providerType) {
        return repository.findByMatchIdAndStatusOrderByCreatedAtDesc(matchId, RideRequestStatus.PENDING)
            .stream()
            .filter(entity -> matchesProviderType(entity.getProviderType(), providerType))
            .map(this::toView)
            .toList();
    }

    public List<RideRequestView> listAssignedForDriver(String driverProfileId) {
        if (driverProfileId == null || driverProfileId.isBlank()) {
            return List.of();
        }
        return repository.findByDriverProfileIdOrderByUpdatedAtDesc(driverProfileId)
            .stream()
            .map(this::toView)
            .toList();
    }

    public RideRequestView accept(UUID id, RideRequestStatusUpdateRequest request) {
        RideRequestEntity entity = require(id);
        entity.setStatus(RideRequestStatus.ACCEPTED);
        entity.setDriverProfileId(normalize(request.getDriverProfileId()));
        entity.setDriverDisplayName(normalize(request.getDriverDisplayName()));
        entity.setDriverPhone(normalize(request.getDriverPhone()));
        entity.setDriverVehiclePlate(normalize(request.getDriverVehiclePlate()));
        entity.setDriverVehicleLabel(normalize(request.getDriverVehicleLabel()));
        entity.setDriverVehicleImageUrl(normalize(request.getDriverVehicleImageUrl()));
        if (entity.getDriverProfileId() == null || entity.getDriverDisplayName() == null || entity.getDriverVehiclePlate() == null || entity.getDriverVehicleLabel() == null || entity.getDriverVehicleImageUrl() == null) {
            var driverProfile = transportProfileService.me();
            if (entity.getDriverProfileId() == null) {
                entity.setDriverProfileId(driverProfile.ownerEmail());
            }
            if (entity.getDriverDisplayName() == null) {
                entity.setDriverDisplayName(firstNonBlank(driverProfile.displayName(), driverProfile.driverName(), driverProfile.ownerName(), driverProfile.ownerEmail()));
            }
            if (entity.getDriverVehiclePlate() == null) {
                entity.setDriverVehiclePlate(normalize(driverProfile.vehiclePlate()));
            }
            if (entity.getDriverVehicleLabel() == null) {
                entity.setDriverVehicleLabel(normalize(driverProfile.vehicleLabel()));
            }
            if (entity.getDriverVehicleImageUrl() == null) {
                entity.setDriverVehicleImageUrl(normalize(driverProfile.vehicleImageUrl()));
            }
        }
        entity.setResponseNote(normalize(request.getResponseNote()));
        if (request.getDriverLatitude() != null && request.getDriverLongitude() != null) {
            entity.setDriverLatitude(request.getDriverLatitude());
            entity.setDriverLongitude(request.getDriverLongitude());
        } else {
            double[] origin = syntheticDriverOrigin(entity);
            entity.setDriverLatitude(origin[0]);
            entity.setDriverLongitude(origin[1]);
        }
        if (request.getDriverDistanceKm() != null) {
            entity.setDriverDistanceKm(request.getDriverDistanceKm());
        } else {
            entity.setDriverDistanceKm(round(distanceKm(entity.getDriverLatitude(), entity.getDriverLongitude(), entity.getPickupLatitude(), entity.getPickupLongitude())));
        }
        if (request.getDriverEtaMinutes() != null) {
            entity.setDriverEtaMinutes(request.getDriverEtaMinutes());
        } else {
            entity.setDriverEtaMinutes(estimateEtaMinutes(entity.getDriverDistanceKm()));
        }
        entity.setPickupReachedAt(null);
        entity.setTripStartedAt(null);
        entity.setTripCompletedAt(null);
        entity.setUpdatedAt(LocalDateTime.now());
        return toView(repository.save(entity));
    }

    public RideRequestView refuse(UUID id, RideRequestStatusUpdateRequest request) {
        RideRequestEntity entity = require(id);
        entity.setStatus(RideRequestStatus.REFUSED);
        entity.setResponseNote(normalize(request.getResponseNote()));
        entity.setUpdatedAt(LocalDateTime.now());
        return toView(repository.save(entity));
    }

    public RideRequestView arrive(UUID id) {
        RideRequestEntity entity = require(id);
        entity.setStatus(RideRequestStatus.ARRIVED_AT_PICKUP);
        entity.setDriverLatitude(entity.getPickupLatitude());
        entity.setDriverLongitude(entity.getPickupLongitude());
        entity.setDriverDistanceKm(0d);
        entity.setDriverEtaMinutes(0);
        entity.setPickupReachedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return toView(repository.save(entity));
    }

    public RideRequestView startTrip(UUID id) {
        RideRequestEntity entity = require(id);
        entity.setStatus(RideRequestStatus.IN_PROGRESS);
        entity.setTripStartedAt(LocalDateTime.now());
        if (entity.getPickupLatitude() != null && entity.getPickupLongitude() != null && entity.getDestinationLatitude() != null && entity.getDestinationLongitude() != null) {
            entity.setJourneyDistanceKm(round(distanceKm(entity.getPickupLatitude(), entity.getPickupLongitude(), entity.getDestinationLatitude(), entity.getDestinationLongitude())));
            entity.setJourneyEtaMinutes(estimateEtaMinutes(entity.getJourneyDistanceKm()));
        }
        entity.setUpdatedAt(LocalDateTime.now());
        return toView(repository.save(entity));
    }

    public RideRequestView completeTrip(UUID id) {
        RideRequestEntity entity = require(id);
        entity.setStatus(RideRequestStatus.COMPLETED);
        entity.setTripCompletedAt(LocalDateTime.now());
        entity.setDriverLatitude(entity.getDestinationLatitude());
        entity.setDriverLongitude(entity.getDestinationLongitude());
        entity.setJourneyDistanceKm(0d);
        entity.setJourneyEtaMinutes(0);
        entity.setUpdatedAt(LocalDateTime.now());
        return toView(repository.save(entity));
    }

    private RideRequestEntity require(UUID id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Ride request not found"));
    }

    private RideRequestView toView(RideRequestEntity entity) {
        RideRequestView view = new RideRequestView();
        view.setId(entity.getId());
        view.setMatchId(entity.getMatchId());
        view.setMatchTitle(entity.getMatchTitle());
        view.setProviderType(entity.getProviderType());
        view.setPassengerEmail(entity.getPassengerEmail());
        view.setPassengerName(entity.getPassengerName());
        view.setPassengerPhone(entity.getPassengerPhone());
        view.setPickupLabel(entity.getPickupLabel());
        view.setPickupLatitude(entity.getPickupLatitude());
        view.setPickupLongitude(entity.getPickupLongitude());
        view.setDestinationLabel(entity.getDestinationLabel());
        view.setDestinationLatitude(entity.getDestinationLatitude());
        view.setDestinationLongitude(entity.getDestinationLongitude());
        view.setStatus(entity.getStatus());
        view.setDriverProfileId(entity.getDriverProfileId());
        view.setDriverDisplayName(entity.getDriverDisplayName());
        view.setDriverPhone(entity.getDriverPhone());
        view.setDriverVehiclePlate(entity.getDriverVehiclePlate());
        view.setDriverVehicleLabel(entity.getDriverVehicleLabel());
        view.setDriverVehicleImageUrl(entity.getDriverVehicleImageUrl());
        view.setDriverLatitude(entity.getDriverLatitude());
        view.setDriverLongitude(entity.getDriverLongitude());
        view.setDriverDistanceKm(entity.getDriverDistanceKm());
        view.setDriverEtaMinutes(entity.getDriverEtaMinutes());
        view.setJourneyDistanceKm(entity.getJourneyDistanceKm());
        view.setJourneyEtaMinutes(entity.getJourneyEtaMinutes());
        view.setPickupReachedAt(entity.getPickupReachedAt());
        view.setTripStartedAt(entity.getTripStartedAt());
        view.setTripCompletedAt(entity.getTripCompletedAt());
        view.setResponseNote(entity.getResponseNote());
        view.setCreatedAt(entity.getCreatedAt());
        view.setUpdatedAt(entity.getUpdatedAt());
        return view;
    }

    private double[] syntheticDriverOrigin(RideRequestEntity entity) {
        double anchorLatitude = entity.getPickupLatitude() != null ? entity.getPickupLatitude() : entity.getDestinationLatitude() != null ? entity.getDestinationLatitude() : 40.4168d;
        double anchorLongitude = entity.getPickupLongitude() != null ? entity.getPickupLongitude() : entity.getDestinationLongitude() != null ? entity.getDestinationLongitude() : -3.7038d;
        double offset = DEFAULT_DRIVER_OFFSET_DEGREES + Math.abs(entity.getMatchId().toLowerCase(Locale.ROOT).hashCode() % 700) / 100000.0;
        return new double[] { anchorLatitude + offset, anchorLongitude - offset };
    }

    private double distanceKm(Double startLatitude, Double startLongitude, Double endLatitude, Double endLongitude) {
        if (startLatitude == null || startLongitude == null || endLatitude == null || endLongitude == null) {
            return 0d;
        }
        double earthRadiusKm = 6371.0d;
        double latDelta = Math.toRadians(endLatitude - startLatitude);
        double lonDelta = Math.toRadians(endLongitude - startLongitude);
        double a = Math.sin(latDelta / 2) * Math.sin(latDelta / 2)
            + Math.cos(Math.toRadians(startLatitude)) * Math.cos(Math.toRadians(endLatitude))
            * Math.sin(lonDelta / 2) * Math.sin(lonDelta / 2);
        return 2 * earthRadiusKm * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }

    private int estimateEtaMinutes(double distanceKm) {
        return Math.max(1, (int) Math.round(distanceKm * 3.0d));
    }

    private double round(double value) {
        return Math.round(value * 10d) / 10d;
    }

    private String normalize(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }

    private String firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.isBlank()) {
                return value.trim();
            }
        }
        return null;
    }

    private boolean matchesProviderType(String requestProviderType, String driverProviderType) {
        String request = normalizeProviderType(requestProviderType);
        String driver = normalizeProviderType(driverProviderType);
        if (request.isBlank() || driver.isBlank()) {
            return false;
        }
        return request.equals(driver) || request.contains(driver) || driver.contains(request);
    }

    private String normalizeProviderType(String value) {
        return value == null ? "" : value.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", "");
    }
}
