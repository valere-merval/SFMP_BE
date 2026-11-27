package com.smartfan.mobility.transport.bootstrap;

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

    public TransportProviderDemoDataSeeder(TransportProviderRepository transportProviderRepository) {
        this.transportProviderRepository = transportProviderRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (transportProviderRepository.count() > 0) {
            return;
        }
        List<TransportProviderEntity> providers = new ArrayList<>();
        for (VenueSeed venue : demoVenues()) {
            providers.add(provider(venue, "Train", "Rail Link", "Platform 2", "TRN-" + venue.idSuffix().toUpperCase(), 0.010, 0.008, "Main rail connection to the stadium"));
            providers.add(provider(venue, "Bus", "Express Bus", "Stop A", "BUS-" + venue.idSuffix().toUpperCase(), -0.006, 0.005, "Direct bus from the city center"));
            providers.add(provider(venue, "Tram", "Arena Tram", "Stop T", "TRM-" + venue.idSuffix().toUpperCase(), 0.004, -0.005, "Tram line serving the matchday crowd"));
            providers.add(provider(venue, "Bike", "Bike Dock", "Dock B", "BIK-" + venue.idSuffix().toUpperCase(), 0.002, 0.002, "Bike dock within a short walk"));
            providers.add(provider(venue, "Scooter", "Scooter Bay", "Bay S", "SCO-" + venue.idSuffix().toUpperCase(), -0.002, -0.002, "Scooter bay near the fan zone"));
        }
        transportProviderRepository.saveAll(providers);
    }

    private TransportProviderEntity provider(VenueSeed venue, String type, String displayName, String vehicleLabel, String plate, double latOffset, double lonOffset, String publicNotes) {
        return new TransportProviderEntity(
                venue.idSuffix() + '-' + type.toLowerCase(),
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
                LocalDateTime.now());
    }

    private List<VenueSeed> demoVenues() {
        return List.of(
                new VenueSeed("rma-bayern-bernabeu", "Madrid", "Spain", "Santiago Bernabéu", 40.4531, -3.6883),
                new VenueSeed("atm-dortmund-metropolitano", "Madrid", "Spain", "Riyadh Air Metropolitano", 40.4362, -3.5997),
                new VenueSeed("bar-manu-campnou", "Barcelona", "Spain", "Spotify Camp Nou", 41.3809, 2.1228),
                new VenueSeed("schalke-paris-veltins", "Gelsenkirchen", "Germany", "Veltins-Arena", 51.5549, 7.0677),
                new VenueSeed("bayern-juventus-allianz", "Munich", "Germany", "Allianz Arena", 48.2188, 11.6247),
                new VenueSeed("dortmund-liverpool-signal", "Dortmund", "Germany", "Signal Iduna Park", 51.4926, 7.4519),
                new VenueSeed("wembley-chelsea-wembley", "London", "United Kingdom", "Wembley Stadium", 51.5560, -0.2796),
                new VenueSeed("stamford-liverpool-bridge", "London", "United Kingdom", "Stamford Bridge", 51.4817, -0.1905),
                new VenueSeed("milan-juventus-san-siro", "Milan", "Italy", "Stadio San Siro", 45.4781, 9.1240),
                new VenueSeed("manu-mancity-oldtrafford", "Manchester", "United Kingdom", "Old Trafford", 53.4631, -2.2913),
                new VenueSeed("mancity-liverpool-etihad", "Manchester", "United Kingdom", "Etihad Stadium", 53.4831, -2.2004),
                new VenueSeed("paris-marseille-parc", "Paris", "France", "Parc des Princes", 48.8414, 2.2530),
                new VenueSeed("marseille-lyon-velodrome", "Marseille", "France", "Orange Vélodrome", 43.2698, 5.3957),
                new VenueSeed("lyon-psg-groupama", "Lyon", "France", "Groupama Stadium", 45.7650, 4.9821));
    }

    private record VenueSeed(String matchId, String city, String country, String stadium, double latitude, double longitude) {
        String idSuffix() {
            return matchId;
        }
    }
}
