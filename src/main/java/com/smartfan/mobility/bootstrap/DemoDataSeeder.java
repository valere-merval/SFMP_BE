package com.smartfan.mobility.catalog.bootstrap;

import com.smartfan.mobility.catalog.domain.EventCatalogEntity;
import com.smartfan.mobility.catalog.domain.EventCatalogRepository;
import com.smartfan.mobility.matches.domain.MatchEntity;
import com.smartfan.mobility.matches.domain.MatchRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DemoDataSeeder implements CommandLineRunner {

    private final EventCatalogRepository eventCatalogRepository;
    private final MatchRepository matchRepository;

    public DemoDataSeeder(EventCatalogRepository eventCatalogRepository, MatchRepository matchRepository) {
        this.eventCatalogRepository = eventCatalogRepository;
        this.matchRepository = matchRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        seedCatalogs();
        seedMatches();
    }

    private void seedCatalogs() {
        if (eventCatalogRepository.count() > 0) {
            return;
        }
        eventCatalogRepository.saveAll(demoCatalogs());
    }

    private void seedMatches() {
        if (matchRepository.count() > 0) {
            return;
        }
        List<MatchEntity> matches = new ArrayList<>();
        for (VenueSeed venue : demoVenues()) {
            matches.add(new MatchEntity(
                    venue.matchId(),
                    venue.catalogId(),
                    venue.homeTeam(),
                    venue.awayTeam(),
                    flagFor(venue.homeTeam()),
                    flagFor(venue.awayTeam()),
                    venue.city(),
                    venue.country(),
                    venue.stadium(),
                    venue.stage(),
                    venue.date(),
                    venue.time(),
                    venue.status(),
                    venue.gate(),
                    venue.sector(),
                    venue.countdown(),
                    venue.weather(),
                    venue.temperature(),
                    venue.transitSummary(),
                    true,
                    venue.ticketPrice(),
                    venue.ticketsTotal(),
                    venue.ticketsRemaining(),
                    venue.imageUrl()));
        }
        matchRepository.saveAll(matches);
    }

    private String flagFor(String team) {
        if (team == null || team.isBlank()) {
            return "generic";
        }
        String normalized = team.toLowerCase()
                .replace("münchen", "munich")
                .replace("atlético", "atletico")
                .replace("olympique de ", "")
                .replace("fc ", "")
                .replace("borussia ", "");
        if (normalized.contains("real madrid")) return "rma";
        if (normalized.contains("atletico")) return "atm";
        if (normalized.contains("barcelona")) return "bar";
        if (normalized.contains("schalke")) return "s04";
        if (normalized.contains("bayern")) return "bay";
        if (normalized.contains("dortmund")) return "bvb";
        if (normalized.contains("chelsea")) return "che";
        if (normalized.contains("liverpool")) return "liv";
        if (normalized.contains("manchester united")) return "mun";
        if (normalized.contains("manchester city")) return "mcf";
        if (normalized.contains("milan")) return "mil";
        if (normalized.contains("juventus")) return "juv";
        if (normalized.contains("paris")) return "psg";
        if (normalized.contains("marseille")) return "oma";
        if (normalized.contains("lyon")) return "oly";
        String safe = normalized.replaceAll("[^a-z0-9]", "");
        return safe.substring(0, Math.min(3, safe.length()));
    }

    private List<EventCatalogEntity> demoCatalogs() {
        return List.of(
                new EventCatalogEntity("europe-elite", "Europe Elite Stadiums", "Champions League level fixtures with the biggest football venues in Europe.", "https://images.unsplash.com/photo-1508098682722-e99c43a406b2?auto=format&fit=crop&w=1200&q=80", "Football", "Europe", "Elite", 4),
                new EventCatalogEntity("world-derbies", "World Derby Nights", "Classic rivalry nights across iconic stadiums and matchday cities.", "https://images.unsplash.com/photo-1518091043644-c1d4457512c6?auto=format&fit=crop&w=1200&q=80", "Football", "Worldwide", "Derby", 4),
                new EventCatalogEntity("city-spotlight", "City Spotlight", "Large-capacity arenas with live transport and mobility coverage.", "https://images.unsplash.com/photo-1522778119026-d647f0596c20?auto=format&fit=crop&w=1200&q=80", "Football", "Europe", "Spotlight", 4),
                new EventCatalogEntity("stadium-icons", "Stadium Icons", "Historic grounds and ultra-modern stadiums with premium wayfinding.", "https://images.unsplash.com/photo-1505666287802-931dc83d6e3a?auto=format&fit=crop&w=1200&q=80", "Football", "Worldwide", "Icons", 4));
    }

    private List<VenueSeed> demoVenues() {
        return List.of(
                new VenueSeed("europe-elite", "rma-bayern-bernabeu", "Real Madrid", "Bayern München", "Madrid", "Spain", "Santiago Bernabéu", "Quarterfinal", LocalDate.of(2026, 12, 9), "21:00", "Live", "Gate 3", "Sector A", "45 min", "Clear", "+18°C", "Train, bus and tram are all active around the stadium.", "€89", 68000, 2400, 40.4531, -3.6883, "https://images.unsplash.com/photo-1547347298-4074fc3086f0?auto=format&fit=crop&w=1200&q=80"),
                new VenueSeed("europe-elite", "atm-dortmund-metropolitano", "Atlético Madrid", "Borussia Dortmund", "Madrid", "Spain", "Riyadh Air Metropolitano", "Round of 16", LocalDate.of(2026, 12, 11), "19:45", "Live", "Gate 7", "Sector B", "51 min", "Cloudy", "+17°C", "Train and bus corridors feed the north and east sides.", "€72", 70000, 2100, 40.4362, -3.5997, "https://images.unsplash.com/photo-1560156408-6d8a2f9b2f09?auto=format&fit=crop&w=1200&q=80"),
                new VenueSeed("world-derbies", "bar-manu-campnou", "FC Barcelona", "Manchester United", "Barcelona", "Spain", "Spotify Camp Nou", "Semifinal", LocalDate.of(2026, 12, 14), "21:00", "Live", "Gate 10", "Sector C", "38 min", "Warm", "+20°C", "Metro, train and bus lines converge near the stadium.", "€94", 99354, 6200, 41.3809, 2.1228, "https://images.unsplash.com/photo-1508098682722-e99c43a406b2?auto=format&fit=crop&w=1200&q=80"),
                new VenueSeed("world-derbies", "schalke-paris-veltins", "Schalke 04", "Paris Saint-Germain", "Gelsenkirchen", "Germany", "Veltins-Arena", "Group Stage", LocalDate.of(2026, 12, 8), "18:30", "Live", "Gate East", "Sector D", "55 min", "Rain", "+11°C", "Tram and regional rail reach the arena directly.", "€61", 62271, 1800, 51.5549, 7.0677, "https://images.unsplash.com/photo-1471295253337-3ceaaedca402?auto=format&fit=crop&w=1200&q=80"),
                new VenueSeed("city-spotlight", "bayern-juventus-allianz", "Bayern München", "Juventus", "Munich", "Germany", "Allianz Arena", "Final", LocalDate.of(2026, 12, 19), "20:00", "Live", "Gate North", "Sector E", "32 min", "Clear", "+9°C", "Suburban rail and bus loops keep matchday flow smooth.", "€105", 75024, 4200, 48.2188, 11.6247, "https://images.unsplash.com/photo-1522778119026-d647f0596c20?auto=format&fit=crop&w=1200&q=80"),
                new VenueSeed("city-spotlight", "dortmund-liverpool-signal", "Borussia Dortmund", "Liverpool", "Dortmund", "Germany", "Signal Iduna Park", "Round of 16", LocalDate.of(2026, 12, 7), "20:45", "Live", "Gate South", "Sector F", "44 min", "Windy", "+10°C", "Rail and tram are the fastest ways to the ground.", "€88", 81365, 3100, 51.4926, 7.4519, "https://images.unsplash.com/photo-1522778289026-86b1fdf3b6e4?auto=format&fit=crop&w=1200&q=80"),
                new VenueSeed("stadium-icons", "wembley-chelsea-wembley", "Chelsea", "Manchester City", "London", "United Kingdom", "Wembley Stadium", "Final", LocalDate.of(2026, 12, 20), "19:30", "Live", "Gate South", "Sector G", "28 min", "Clear", "+8°C", "Rail, underground links and buses keep the stadium accessible.", "£92", 90000, 4500, 51.5560, -0.2796, "https://images.unsplash.com/photo-1508098682722-e99c43a406b2?auto=format&fit=crop&w=1200&q=80"),
                new VenueSeed("stadium-icons", "stamford-liverpool-bridge", "Chelsea", "Liverpool", "London", "United Kingdom", "Stamford Bridge", "Friendly", LocalDate.of(2026, 12, 5), "17:15", "Live", "Gate West", "Sector H", "24 min", "Overcast", "+7°C", "Bus and rail connections are available within a short walk.", "£54", 40341, 1200, 51.4817, -0.1905, "https://images.unsplash.com/photo-1494755997001-8f4d3f03f8e5?auto=format&fit=crop&w=1200&q=80"),
                new VenueSeed("europe-elite", "milan-juventus-san-siro", "Milan", "Juventus", "Milan", "Italy", "Stadio San Siro", "Derby", LocalDate.of(2026, 12, 16), "20:45", "Live", "Gate North", "Sector I", "33 min", "Clear", "+12°C", "Tram and suburban rail are the default matchday options.", "€76", 75923, 3300, 45.4781, 9.1240, "https://images.unsplash.com/photo-1501386761578-eac5c94b800a?auto=format&fit=crop&w=1200&q=80"),
                new VenueSeed("world-derbies", "manu-mancity-oldtrafford", "Manchester United", "Manchester City", "Manchester", "United Kingdom", "Old Trafford", "Derby", LocalDate.of(2026, 12, 13), "16:30", "Live", "Gate South", "Sector J", "39 min", "Rain", "+6°C", "Rail and bus services converge before kick-off.", "£85", 74879, 2800, 53.4631, -2.2913, "https://images.unsplash.com/photo-1518091043644-c1d4457512c6?auto=format&fit=crop&w=1200&q=80"),
                new VenueSeed("world-derbies", "mancity-liverpool-etihad", "Manchester City", "Liverpool", "Manchester", "United Kingdom", "Etihad Stadium", "League Night", LocalDate.of(2026, 12, 21), "20:00", "Live", "Gate East", "Sector K", "31 min", "Clear", "+6°C", "Tram and shuttle buses provide the fastest access.", "£79", 55097, 2400, 53.4831, -2.2004, "https://images.unsplash.com/photo-1499036578602-0edc0f1e8b3c?auto=format&fit=crop&w=1200&q=80"),
                new VenueSeed("stadium-icons", "paris-marseille-parc", "Paris SG", "Olympique de Marseille", "Paris", "France", "Parc des Princes", "Derby", LocalDate.of(2026, 12, 15), "21:00", "Live", "Gate South", "Sector L", "36 min", "Clear", "+10°C", "Metro, bus and walk-up routes all stay open for matchday.", "€83", 47929, 2100, 48.8414, 2.2530, "https://images.unsplash.com/photo-1504307651254-35680f356dfd?auto=format&fit=crop&w=1200&q=80"),
                new VenueSeed("city-spotlight", "marseille-lyon-velodrome", "Olympique de Marseille", "Olympique Lyonnais", "Marseille", "France", "Orange Vélodrome", "League Night", LocalDate.of(2026, 12, 18), "20:45", "Live", "Gate West", "Sector M", "42 min", "Clear", "+13°C", "Tram and bus lines cover the stadium ring road.", "€69", 67394, 2600, 43.2698, 5.3957, "https://images.unsplash.com/photo-1547347298-4074fc3086f0?auto=format&fit=crop&w=1200&q=80"),
                new VenueSeed("stadium-icons", "lyon-psg-groupama", "Olympique Lyonnais", "Paris SG", "Lyon", "France", "Groupama Stadium", "Cup Night", LocalDate.of(2026, 12, 22), "19:00", "Live", "Gate North", "Sector N", "27 min", "Cloudy", "+11°C", "Train, tram and shuttle access are active.", "€73", 59186, 1750, 45.7650, 4.9821, "https://images.unsplash.com/photo-1471295253337-3ceaaedca402?auto=format&fit=crop&w=1200&q=80"));
    }

    private record VenueSeed(
            String catalogId,
            String matchId,
            String homeTeam,
            String awayTeam,
            String city,
            String country,
            String stadium,
            String stage,
            LocalDate date,
            String time,
            String status,
            String gate,
            String sector,
            String countdown,
            String weather,
            String temperature,
            String transitSummary,
            String ticketPrice,
            Integer ticketsTotal,
            Integer ticketsRemaining,
            double latitude,
            double longitude,
            String imageUrl) {}
}
