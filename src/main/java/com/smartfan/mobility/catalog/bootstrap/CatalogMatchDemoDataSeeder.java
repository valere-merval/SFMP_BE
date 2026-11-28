package com.smartfan.mobility.catalog.bootstrap;

import com.smartfan.mobility.catalog.domain.EventCatalogEntity;
import com.smartfan.mobility.catalog.domain.EventCatalogRepository;
import com.smartfan.mobility.matches.domain.MatchEntity;
import com.smartfan.mobility.matches.domain.MatchRepository;
import com.smartfan.mobility.shared.bootstrap.CompetitionDemoData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Profile("flyway-java-demo")
@Order(10)
public class CatalogMatchDemoDataSeeder implements CommandLineRunner {

    private final EventCatalogRepository eventCatalogRepository;
    private final MatchRepository matchRepository;

    public CatalogMatchDemoDataSeeder(EventCatalogRepository eventCatalogRepository, MatchRepository matchRepository) {
        this.eventCatalogRepository = eventCatalogRepository;
        this.matchRepository = matchRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        matchRepository.deleteAllInBatch();
        eventCatalogRepository.deleteAllInBatch();

        List<CompetitionDemoData.MatchSeed> matches = CompetitionDemoData.matchSeeds();
        Map<String, Long> counts = matches.stream().collect(Collectors.groupingBy(CompetitionDemoData.MatchSeed::catalogId, HashMap::new, Collectors.counting()));

        eventCatalogRepository.saveAll(CompetitionDemoData.competitionSeeds().stream()
                .map(seed -> new EventCatalogEntity(
                        seed.id(),
                        seed.title(),
                        seed.description(),
                        seed.imageUrl(),
                        seed.sport(),
                        seed.country(),
                        seed.badge(),
                        counts.getOrDefault(seed.id(), 0L).intValue()))
                .toList());

        matchRepository.saveAll(matches.stream()
                .map(match -> new MatchEntity(
                        match.id(),
                        match.catalogId(),
                        match.homeTeam(),
                        match.awayTeam(),
                        match.homeFlag(),
                        match.awayFlag(),
                        match.city(),
                        match.country(),
                        match.venue(),
                        match.stage(),
                        match.date(),
                        match.time(),
                        "Scheduled",
                        match.gate(),
                        match.sector(),
                        match.countdown(),
                        match.weather(),
                        match.temperature(),
                        match.transitSummary(),
                        true,
                        "Available",
                        60000,
                        8000,
                        CompetitionDemoData.stadiumImageUrl(match.venue(), match.city())))
                .toList());
    }
}
