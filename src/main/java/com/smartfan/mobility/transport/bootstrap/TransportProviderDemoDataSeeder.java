package com.smartfan.mobility.transport.bootstrap;

import com.smartfan.mobility.shared.bootstrap.DemoVenueData;
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
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
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
            for (DemoVenueData.VenueSeed venue : DemoVenueData.matchSeeds()) {
                providers.add(provider(venue, "Train", "Rail Link", "Platform 2", "TRN-" + venue.matchId().toUpperCase(), 0.010, 0.008, "Main rail connection to the stadium", createdAt));
                providers.add(provider(venue, "Bus", "Express Bus", "Stop A", "BUS-" + venue.matchId().toUpperCase(), -0.006, 0.005, "Direct bus from the city center", createdAt));
                providers.add(provider(venue, "Tram", "Arena Tram", "Stop T", "TRM-" + venue.matchId().toUpperCase(), 0.004, -0.005, "Tram line serving the matchday crowd", createdAt));
            }
            transportProviderRepository.saveAll(providers);
        }

        if (transportProfileRepository.count() == 0) {
            List<TransportProfileEntity> profiles = new ArrayList<>();
            LocalDateTime now = LocalDateTime.now();
            for (DemoVenueData.VenueSeed venue : DemoVenueData.matchSeeds()) {
                profiles.add(profile(venue, "Train", "Rail Link", "Platform 2", "TRN-" + venue.matchId().toUpperCase(), now));
                profiles.add(profile(venue, "Bus", "Express Bus", "Stop A", "BUS-" + venue.matchId().toUpperCase(), now));
                profiles.add(profile(venue, "Tram", "Arena Tram", "Stop T", "TRM-" + venue.matchId().toUpperCase(), now));
            }
            transportProfileRepository.saveAll(profiles);
        }

        if (availabilityRepository.count() == 0) {
            List<TransportProfileAvailabilityEntity> availabilities = new ArrayList<>();
            LocalDateTime now = LocalDateTime.now();
            for (TransportProfileEntity profile : transportProfileRepository.findAll()) {
                availabilities.add(new TransportProfileAvailabilityEntity(
                        profile.getId() + "-availability",
                        profile.getId(),
                        profile.getId().substring(0, profile.getId().lastIndexOf('-')),
                        true,
                        true,
                        "Available",
                        "Demo data seeded at startup",
                        now,
                        now));
            }
            availabilityRepository.saveAll(availabilities);
        }
    }

    private TransportProviderEntity provider(
            DemoVenueData.VenueSeed venue,
            String type,
            String displayName,
            String vehicleLabel,
            String plate,
            double latOffset,
            double lonOffset,
            String publicNotes,
            LocalDateTime createdAt) {
        return new TransportProviderEntity(
                venue.matchId() + '-' + type.toLowerCase(),
                venue.matchId(),
                type,
                venue.city() + ' ' + displayName,
                displayName + " Ops",
                vehicleLabel,
                plate,
                venue.city(),
                venue.country(),
                venue.stadium(),
                venue.stadium() + ", " + venue.city() + ", " + venue.country(),
                venue.latitude() + latOffset,
                venue.longitude() + lonOffset,
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
            DemoVenueData.VenueSeed venue,
            String type,
            String displayName,
            String vehicleLabel,
            String plate,
            LocalDateTime now) {
        String id = venue.matchId() + '-' + type.toLowerCase();
        return new TransportProfileEntity(
                id,
                "demo+" + id + "@sfmp.local",
                displayName + " Operator",
                type,
                venue.city() + ' ' + displayName,
                displayName + " Ops",
                vehicleLabel,
                plate,
                venue.city(),
                venue.country(),
                venue.stadium(),
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
