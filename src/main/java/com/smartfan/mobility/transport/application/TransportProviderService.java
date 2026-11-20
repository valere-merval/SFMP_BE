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

    public TransportProviderService(TransportProviderRepository transportProviderRepository, MatchRepository matchRepository) {
        this.transportProviderRepository = transportProviderRepository;
        this.matchRepository = matchRepository;
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
        List<TransportProviderEntity> providers;
        if (matchId == null || matchId.isBlank()) {
            providers = transportProviderRepository.findAllByOrderByCreatedAtDesc();
        } else {
            providers = transportProviderRepository.findByMatchIdOrderByCreatedAtDesc(matchId);
        }
        return providers;
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
                entity.getCountry());
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
