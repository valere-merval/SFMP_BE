package com.smartfan.mobility;

import static org.assertj.core.api.Assertions.assertThat;

import com.smartfan.mobility.catalog.domain.EventCatalogRepository;
import com.smartfan.mobility.matches.domain.MatchRepository;
import com.smartfan.mobility.transport.domain.TransportProviderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoDataSeederTest {

    @Autowired
    private EventCatalogRepository eventCatalogRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private TransportProviderRepository transportProviderRepository;

    @Test
    void seeds_demo_catalog_matches_and_transport_data() {
        assertThat(eventCatalogRepository.count()).isEqualTo(4);
        assertThat(matchRepository.count()).isEqualTo(14);
        assertThat(transportProviderRepository.count()).isEqualTo(70);
    }
}
