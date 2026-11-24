package com.smartfan.mobility.transport.application;

import com.smartfan.mobility.auth.application.AuthService;
import com.smartfan.mobility.auth.domain.AuthUser;
import com.smartfan.mobility.matches.domain.MatchRepository;
import com.smartfan.mobility.shared.catalogue.FixtureCatalogue;
import com.smartfan.mobility.transport.api.TransportAvailabilityRequest;
import com.smartfan.mobility.transport.api.TransportAvailabilityView;
import com.smartfan.mobility.transport.api.TransportProfileRequest;
import com.smartfan.mobility.transport.api.TransportProfileView;
import com.smartfan.mobility.transport.domain.TransportProfileAvailabilityEntity;
import com.smartfan.mobility.transport.domain.TransportProfileAvailabilityRepository;
import com.smartfan.mobility.transport.domain.TransportProfileEntity;
import com.smartfan.mobility.transport.domain.TransportProfileRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransportProfileService {

    private final TransportProfileRepository profileRepository;
    private final TransportProfileAvailabilityRepository availabilityRepository;
    private final MatchRepository matchRepository;
    private final AuthService authService;

    public TransportProfileService(
            TransportProfileRepository profileRepository,
            TransportProfileAvailabilityRepository availabilityRepository,
            MatchRepository matchRepository,
            AuthService authService) {
        this.profileRepository = profileRepository;
        this.availabilityRepository = availabilityRepository;
        this.matchRepository = matchRepository;
        this.authService = authService;
    }

    @Transactional(readOnly = true)
    public TransportProfileView me() {
        AuthUser user = currentUser();
        return profileRepository.findByOwnerEmail(user.email())
                .map(this::toView)
                .orElseGet(() -> new TransportProfileView("", user.email(), user.fullName(), "Taxi", user.fullName(), user.fullName(), "", "", "", "", "", "", "", "", "", "", "", false, null, null));
    }

    @Transactional
    public TransportProfileView upsertMe(TransportProfileRequest request) {
        AuthUser user = currentUser();
        LocalDateTime now = LocalDateTime.now();
        TransportProfileEntity entity = profileRepository.findByOwnerEmail(user.email())
                .orElseGet(() -> new TransportProfileEntity(
                        normalizeId(user.email(), request.displayName(), request.vehiclePlate()),
                        user.email(),
                        user.fullName(),
                        safe(request.providerType(), "Taxi"),
                        safe(request.displayName(), user.fullName()),
                        safe(request.driverName(), user.fullName()),
                        safe(request.vehicleLabel(), "Vehicle"),
                        safe(request.vehiclePlate(), "N/A"),
                        safe(request.city(), ""),
                        safe(request.country(), ""),
                        safe(request.zone(), "Match zone"),
                        safe(request.permitUrl(), ""),
                        safe(request.registrationUrl(), ""),
                        safe(request.driverLicenseUrl(), ""),
                        safe(request.vehicleImageUrl(), ""),
                        safe(request.notes(), ""),
                        safe(request.publicNotes(), "Visible to fans when available for the match."),
                        request.active() == null || request.active(),
                        now,
                        now));

        entity.update(
                user.fullName(),
                safe(request.providerType(), entity.getProviderType()),
                safe(request.displayName(), entity.getDisplayName()),
                safe(request.driverName(), user.fullName()),
                safe(request.vehicleLabel(), entity.getVehicleLabel()),
                safe(request.vehiclePlate(), entity.getVehiclePlate()),
                safe(request.city(), entity.getCity()),
                safe(request.country(), entity.getCountry()),
                safe(request.zone(), entity.getZone()),
                safe(request.permitUrl(), entity.getPermitUrl()),
                safe(request.registrationUrl(), entity.getRegistrationUrl()),
                safe(request.driverLicenseUrl(), entity.getDriverLicenseUrl()),
                safe(request.vehicleImageUrl(), entity.getVehicleImageUrl()),
                safe(request.notes(), entity.getNotes()),
                safe(request.publicNotes(), entity.getPublicNotes()),
                request.active() == null ? entity.isActive() : request.active(),
                now);
        profileRepository.save(entity);
        return toView(entity);
    }

    @Transactional(readOnly = true)
    public List<TransportProfileView> listProfiles() {
        return profileRepository.findAllByOrderByUpdatedAtDesc().stream().map(this::toView).toList();
    }

    @Transactional(readOnly = true)
    public List<FixtureCatalogue.TransportProviderView> listPublic(String matchId) {
        if (matchId == null || matchId.isBlank()) {
            return profileRepository.findAllByOrderByUpdatedAtDesc().stream()
                    .filter(TransportProfileEntity::isActive)
                    .map(profile -> availabilityRepository.findByProfileIdOrderByUpdatedAtDesc(profile.getId()).stream()
                            .findFirst()
                            .filter(TransportProfileAvailabilityEntity::isAvailable)
                            .map(availability -> toPublicView(profile, availability))
                            .orElse(null))
                    .filter(profile -> profile != null)
                    .toList();
        }
        return availabilityRepository.findByMatchIdAndAvailableTrueOrderByUpdatedAtDesc(matchId).stream()
                .map(availability -> profileRepository.findById(availability.getProfileId())
                        .map(profile -> toPublicView(profile, availability))
                        .orElse(null))
                .filter(profile -> profile != null)
                .toList();
    }

    @Transactional
    public TransportAvailabilityView setAvailability(String matchId, TransportAvailabilityRequest request) {
        AuthUser user = currentUser();
        TransportProfileEntity profile = profileRepository.findByOwnerEmail(user.email())
                .orElseGet(() -> createPlaceholderProfile(user));
        String safeMatchId = safe(matchId);
        LocalDateTime now = LocalDateTime.now();
        TransportProfileAvailabilityEntity entity = availabilityRepository.findByProfileIdAndMatchId(profile.getId(), safeMatchId)
                .orElseGet(() -> new TransportProfileAvailabilityEntity(
                        normalizeId(profile.getId(), safeMatchId, user.email()),
                        profile.getId(),
                        safeMatchId,
                        true,
                        false,
                        "Available",
                        "",
                        now,
                        now));
        boolean available = request.available() == null || request.available();
        entity.update(
                available,
                available,
                available ? "Available" : "Not available",
                safe(request.notes(), entity.getNotes()),
                now);
        availabilityRepository.save(entity);
        return toAvailabilityView(entity);
    }

    @Transactional(readOnly = true)
    public TransportAvailabilityView getAvailability(String matchId) {
        AuthUser user = currentUser();
        TransportProfileEntity profile = profileRepository.findByOwnerEmail(user.email())
                .orElseGet(() -> createPlaceholderProfile(user));
        return availabilityRepository.findByProfileIdAndMatchId(profile.getId(), safe(matchId))
                .map(this::toAvailabilityView)
                .orElseGet(() -> new TransportAvailabilityView("", profile.getId(), safe(matchId), matchTitle(matchId), false, false, "Not available", "", null, null));
    }

    private TransportProfileEntity createPlaceholderProfile(AuthUser user) {
        LocalDateTime now = LocalDateTime.now();
        TransportProfileEntity profile = new TransportProfileEntity(
                normalizeId(user.email(), user.fullName(), user.email()),
                user.email(),
                user.fullName(),
                "Taxi",
                user.fullName(),
                user.fullName(),
                "Vehicle",
                "N/A",
                "",
                "",
                "Match zone",
                "",
                "",
                "",
                "",
                "",
                "Visible to fans when available for the match.",
                false,
                now,
                now);
        return profileRepository.save(profile);
    }

    private TransportProfileView toView(TransportProfileEntity entity) {
        return new TransportProfileView(
                entity.getId(),
                entity.getOwnerEmail(),
                entity.getOwnerName(),
                entity.getProviderType(),
                entity.getDisplayName(),
                entity.getDriverName(),
                entity.getVehicleLabel(),
                entity.getVehiclePlate(),
                entity.getCity(),
                entity.getCountry(),
                entity.getZone(),
                entity.getPermitUrl(),
                entity.getRegistrationUrl(),
                entity.getDriverLicenseUrl(),
                entity.getVehicleImageUrl(),
                entity.getNotes(),
                entity.getPublicNotes(),
                entity.isActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    private FixtureCatalogue.TransportProviderView toPublicView(TransportProfileEntity profile, TransportProfileAvailabilityEntity availability) {
        boolean available = availability != null && availability.isAvailable();
        boolean ready = availability != null && availability.isReady();
        String status = availability == null ? "Not available" : (available ? "Available" : "Not available");
        return new FixtureCatalogue.TransportProviderView(
                profile.getId(),
                availability == null ? "" : availability.getMatchId(),
                availability == null ? "" : matchTitle(availability.getMatchId()),
                profile.getProviderType(),
                profile.getDisplayName(),
                profile.getVehicleLabel(),
                profile.getZone(),
                status + (ready ? " · Ready" : ""),
                available,
                profile.getCity(),
                profile.getCountry(),
                profile.getOwnerName(),
                profile.getDriverName(),
                profile.getVehiclePlate(),
                ready,
                profile.getPublicNotes());
    }

    private TransportAvailabilityView toAvailabilityView(TransportProfileAvailabilityEntity entity) {
        return new TransportAvailabilityView(
                entity.getId(),
                entity.getProfileId(),
                entity.getMatchId(),
                matchTitle(entity.getMatchId()),
                entity.isAvailable(),
                entity.isReady(),
                entity.getStatus(),
                entity.getNotes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    private String matchTitle(String matchId) {
        if (matchId == null || matchId.isBlank()) {
            return "";
        }
        return matchRepository.findById(matchId)
                .map(match -> match.getHomeTeam() + " vs " + match.getAwayTeam())
                .orElse("");
    }

    private AuthUser currentUser() {
        return authService.currentUser();
    }

    private String normalizeId(String ownerEmail, String displayName, String vehiclePlate) {
        String base = safe(displayName, safe(vehiclePlate, ownerEmail))
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-+|-+$", "");
        if (base.isBlank()) {
            base = "transport-profile";
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
