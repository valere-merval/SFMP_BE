package com.smartfan.mobility.transport.application;

import com.smartfan.mobility.matches.domain.MatchRepository;
import com.smartfan.mobility.shared.catalogue.FixtureCatalogue;
import com.smartfan.mobility.transport.api.TransportProviderAdminView;
import com.smartfan.mobility.transport.api.TransportProviderCreateRequest;
import com.smartfan.mobility.transport.domain.TransportProviderEntity;
import com.smartfan.mobility.transport.domain.TransportProviderRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransportProviderService {

    private final TransportProviderRepository transportProviderRepository;
    private final MatchRepository matchRepository;
    private final TransportGeocodingService geocodingService;

    public TransportProviderService(
            TransportProviderRepository transportProviderRepository,
            MatchRepository matchRepository,
            TransportGeocodingService geocodingService) {
        this.transportProviderRepository = transportProviderRepository;
        this.matchRepository = matchRepository;
        this.geocodingService = geocodingService;
    }

    @Transactional(readOnly = true)
    public List<FixtureCatalogue.TransportProviderView> listPublic(String matchId) {
        return stream(matchId).stream().map(this::toPublicView).toList();
    }

    @Transactional(readOnly = true)
    public List<TransportProviderAdminView> listAdmin(String matchId) {
        return stream(matchId).stream().map(this::toAdminView).toList();
    }

    @Transactional
    public TransportProviderAdminView create(TransportProviderCreateRequest request) {
        String id = normalizeId(request.id(), request.displayName(), request.vehiclePlate());
        String stationAddress = safe(request.stationAddress(), buildStationAddress(request.city(), request.country(), request.zone()));
        TransportGeocodingService.Coordinate coordinate = coordinateFor(request.stationLatitude(), request.stationLongitude(), stationAddress);
        TransportProviderEntity entity = new TransportProviderEntity(
                id,
                safe(request.matchId()),
                safe(request.providerType(), "Taxi"),
                safe(request.displayName(), "Unnamed operator"),
                safe(request.driverName(), "Driver"),
                safe(request.vehicleLabel(), "Vehicle"),
                safe(request.vehiclePlate(), "N/A"),
                safe(request.city(), ""),
                safe(request.country(), ""),
                safe(request.zone(), "Match zone"),
                stationAddress,
                coordinate == null ? null : coordinate.latitude(),
                coordinate == null ? null : coordinate.longitude(),
                safe(request.status(), "Available"),
                request.active() == null || request.active(),
                safe(request.permitUrl(), ""),
                safe(request.registrationUrl(), ""),
                safe(request.driverLicenseUrl(), ""),
                safe(request.vehicleImageUrl(), ""),
                safe(request.notes(), ""),
                safe(request.publicNotes(), ""),
                LocalDateTime.now());
        transportProviderRepository.save(entity);
        return toAdminView(entity);
    }

    private List<TransportProviderEntity> stream(String matchId) {
        if (matchId == null || matchId.isBlank()) {
            return transportProviderRepository.findAllByOrderByCreatedAtDesc();
        }
        return transportProviderRepository.findByMatchIdOrderByCreatedAtDesc(matchId);
    }

    private FixtureCatalogue.TransportProviderView toPublicView(TransportProviderEntity entity) {
        return new FixtureCatalogue.TransportProviderView(
                entity.getId(),
                entity.getMatchId(),
                matchTitle(entity.getMatchId()),
                entity.getProviderType(),
                entity.getDisplayName(),
                entity.getVehicleLabel(),
                entity.getZone(),
                entity.getStatus(),
                entity.isActive(),
                entity.getCity(),
                entity.getCountry(),
                entity.getStationAddress(),
                entity.getStationLatitude(),
                entity.getStationLongitude(),
                entity.getDisplayName(),
                entity.getDriverName(),
                entity.getVehiclePlate(),
                entity.isActive(),
                entity.getPublicNotes());
    }

    private TransportProviderAdminView toAdminView(TransportProviderEntity entity) {
        return new TransportProviderAdminView(
                entity.getId(),
                entity.getMatchId(),
                matchTitle(entity.getMatchId()),
                entity.getProviderType(),
                entity.getDisplayName(),
                entity.getDriverName(),
                entity.getVehicleLabel(),
                entity.getVehiclePlate(),
                entity.getCity(),
                entity.getCountry(),
                entity.getZone(),
                entity.getStationAddress(),
                entity.getStationLatitude(),
                entity.getStationLongitude(),
                entity.getStatus(),
                entity.isActive(),
                entity.getPermitUrl(),
                entity.getRegistrationUrl(),
                entity.getDriverLicenseUrl(),
                entity.getVehicleImageUrl(),
                entity.getNotes(),
                entity.getPublicNotes(),
                entity.getCreatedAt());
    }

    private TransportGeocodingService.Coordinate coordinateFor(Double latitude, Double longitude, String stationAddress) {
        if (latitude != null && longitude != null && Double.isFinite(latitude) && Double.isFinite(longitude)) {
            return new TransportGeocodingService.Coordinate(latitude, longitude);
        }
        return geocodingService.resolve(stationAddress).orElse(null);
    }

    private String buildStationAddress(String city, String country, String zone) {
        StringBuilder builder = new StringBuilder();
        append(builder, zone);
        append(builder, city);
        append(builder, country);
        return builder.length() == 0 ? "" : builder.toString();
    }

    private void append(StringBuilder builder, String value) {
        String cleaned = safe(value);
        if (cleaned.isBlank()) {
            return;
        }
        if (builder.length() > 0) {
            builder.append(", ");
        }
        builder.append(cleaned);
    }

    private String matchTitle(String matchId) {
        if (matchId == null || matchId.isBlank()) {
            return "";
        }
        return matchRepository.findById(matchId)
                .map(match -> match.getHomeTeam() + " vs " + match.getAwayTeam())
                .orElse("");
    }

    private String normalizeId(String id, String displayName, String vehiclePlate) {
        if (id != null && !id.isBlank()) {
            return id;
        }
        String base = safe(displayName, safe(vehiclePlate, "transport"))
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-+|-+$", "");
        if (base.isBlank()) {
            base = "transport";
        }
        return base + "-" + UUID.randomUUID().toString().substring(0, 8);
    }

    private String safe(String value) {
        return value == null ? "" : value.trim();
    }

    private String safe(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value.trim();
    }

}
