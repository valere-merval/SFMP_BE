package com.smartfan.mobility.bootstrap;

import com.smartfan.mobility.catalog.domain.EventCatalogRepository;
import com.smartfan.mobility.matches.domain.MatchRepository;
import com.smartfan.mobility.mobility.domain.MobilityOptionRepository;
import com.smartfan.mobility.transport.domain.TransportProfileAvailabilityRepository;
import com.smartfan.mobility.transport.domain.TransportProfileRepository;
import com.smartfan.mobility.transport.domain.TransportProviderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Order(0)
public class DemoDataResetSeeder implements CommandLineRunner {

    private final TransportProfileAvailabilityRepository availabilityRepository;
    private final TransportProviderRepository transportProviderRepository;
    private final TransportProfileRepository transportProfileRepository;
    private final MobilityOptionRepository mobilityOptionRepository;
    private final MatchRepository matchRepository;
    private final EventCatalogRepository eventCatalogRepository;

    public DemoDataResetSeeder(
            TransportProfileAvailabilityRepository availabilityRepository,
            TransportProviderRepository transportProviderRepository,
            TransportProfileRepository transportProfileRepository,
            MobilityOptionRepository mobilityOptionRepository,
            MatchRepository matchRepository,
            EventCatalogRepository eventCatalogRepository) {
        this.availabilityRepository = availabilityRepository;
        this.transportProviderRepository = transportProviderRepository;
        this.transportProfileRepository = transportProfileRepository;
        this.mobilityOptionRepository = mobilityOptionRepository;
        this.matchRepository = matchRepository;
        this.eventCatalogRepository = eventCatalogRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        availabilityRepository.deleteAllInBatch();
        transportProviderRepository.deleteAllInBatch();
        transportProfileRepository.deleteAllInBatch();
        mobilityOptionRepository.deleteAllInBatch();
        matchRepository.deleteAllInBatch();
        eventCatalogRepository.deleteAllInBatch();
    }
}
