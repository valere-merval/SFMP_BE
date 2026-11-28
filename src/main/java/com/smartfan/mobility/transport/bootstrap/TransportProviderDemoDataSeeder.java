package com.smartfan.mobility.transport.bootstrap;

import com.smartfan.mobility.shared.bootstrap.CompetitionDemoData;
import com.smartfan.mobility.transport.domain.TransportProfileAvailabilityEntity;
import com.smartfan.mobility.transport.domain.TransportProfileAvailabilityRepository;
import com.smartfan.mobility.transport.domain.TransportProfileEntity;
import com.smartfan.mobility.transport.domain.TransportProfileRepository;
import com.smartfan.mobility.transport.domain.TransportProviderEntity;
import com.smartfan.mobility.transport.domain.TransportProviderRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Order(20)
public class TransportProviderDemoDataSeeder implements CommandLineRunner {

    private final TransportProviderRepository transportProviderRepository;
    private final TransportProfileRepository transportProfileRepository;
    private final TransportProfileAvailabilityRepository availabilityRepository;

    public TransportProviderDemoDataSeeder(
            TransportProviderRepository transportProviderRepository,
            TransportProfileRepository transportProfileRepository,
            TransportProfileAvailabilityRepository availabilityRepository) {
        this.transportProviderRepository = transportProviderRepository;
        this.transportProfileRepository = transportProfileRepository;
        this.availabilityRepository = availabilityRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (transportProviderRepository.count() == 0) {
            List<TransportProviderEntity> providers = new ArrayList<>();
            LocalDateTime createdAt = LocalDateTime.now();
            for (CompetitionDemoData.MatchSeed match : CompetitionDemoData.matchSeeds()) {
                providers.add(provider(match, "Train", "Rail Link", "Platform 2", "TRN-" + match.id().toUpperCase(), 0.010, 0.008, "Main rail connection to the stadium", createdAt));
                providers.add(provider(match, "Bus", "Express Bus", "Stop A", "BUS-" + match.id().toUpperCase(), -0.006, 0.005, "Direct bus from the city center", createdAt));
                providers.add(provider(match, "Tram", "Arena Tram", "Stop T", "TRM-" + match.id().toUpperCase(), 0.004, -0.005, "Tram line serving the matchday crowd", createdAt));
                providers.add(provider(match, "Metro", "Metro Line", "Underground Gate", "MET-" + match.id().toUpperCase(), -0.002, -0.008, "Metro connection for the matchday crowd", createdAt));
            }
            transportProviderRepository.saveAll(providers);
        }

        if (transportProfileRepository.count() == 0) {
            List<TransportProfileEntity> profiles = new ArrayList<>();
            LocalDateTime now = LocalDateTime.now();
            for (CompetitionDemoData.MatchSeed match : CompetitionDemoData.matchSeeds()) {
                profiles.add(profile(match, "Train", "Rail Link", "Platform 2", "TRN-" + match.id().toUpperCase(), now));
                profiles.add(profile(match, "Bus", "Express Bus", "Stop A", "BUS-" + match.id().toUpperCase(), now));
                profiles.add(profile(match, "Tram", "Arena Tram", "Stop T", "TRM-" + match.id().toUpperCase(), now));
                profiles.add(profile(match, "Metro", "Metro Line", "Underground Gate", "MET-" + match.id().toUpperCase(), now));
            }
            transportProfileRepository.saveAll(profiles);
        }

        if (availabilityRepository.count() == 0) {
            List<TransportProfileAvailabilityEntity> availabilities = new ArrayList<>();
            LocalDateTime now = LocalDateTime.now();
            for (CompetitionDemoData.MatchSeed match : CompetitionDemoData.matchSeeds()) {
                availabilities.add(availability(match, "Train", now));
                availabilities.add(availability(match, "Bus", now));
                availabilities.add(availability(match, "Tram", now));
                availabilities.add(availability(match, "Metro", now));
            }
            availabilityRepository.saveAll(availabilities);
        }
    }

    private TransportProfileAvailabilityEntity availability(CompetitionDemoData.MatchSeed match, String type, LocalDateTime now) {
        String profileId = match.id() + '-' + type.toLowerCase();
        return new TransportProfileAvailabilityEntity(
                match.id() + '-' + type.toLowerCase() + "-availability",
                profileId,
                match.id(),
                true,
                true,
                "Available",
                "Demo data seeded at startup",
                now,
                now);
    }

    private TransportProviderEntity provider(
            CompetitionDemoData.MatchSeed match,
            String type,
            String displayName,
            String vehicleLabel,
            String plate,
            double latOffset,
            double lonOffset,
            String publicNotes,
            LocalDateTime createdAt) {
        return new TransportProviderEntity(
                match.id() + '-' + type.toLowerCase(),
                match.id(),
                type,
                match.city() + ' ' + displayName,
                displayName + " Ops",
                vehicleLabel,
                plate,
                match.city(),
                match.country(),
                match.venue(),
                match.venue() + ", " + match.city() + ", " + match.country(),
                match.latitude() + latOffset,
                match.longitude() + lonOffset,
                "Available",
                true,
                "",
                "",
                "",
                "",
                "Demo data seeded at startup",
                publicNotes,
                createdAt);
    }

    private TransportProfileEntity profile(
            CompetitionDemoData.MatchSeed match,
            String type,
            String displayName,
            String vehicleLabel,
            String plate,
            LocalDateTime now) {
        String id = match.id() + '-' + type.toLowerCase();
        return new TransportProfileEntity(
                id,
                "demo+" + id + "@sfmp.local",
                displayName + " Operator",
                type,
                match.city() + ' ' + displayName,
                displayName + " Ops",
                vehicleLabel,
                plate,
                match.city(),
                match.country(),
                match.venue(),
                "",
                "",
                "",
                "",
                "Demo data seeded at startup",
                type + " available for matchday fans.",
                true,
                now,
                now);
    }
}
