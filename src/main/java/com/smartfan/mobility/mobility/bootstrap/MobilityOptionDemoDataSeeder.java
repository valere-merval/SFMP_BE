package com.smartfan.mobility.mobility.bootstrap;

import com.smartfan.mobility.mobility.domain.MobilityOptionEntity;
import com.smartfan.mobility.mobility.domain.MobilityOptionRepository;
import com.smartfan.mobility.shared.bootstrap.CompetitionDemoData;
import java.time.LocalDateTime;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Order(30)
public class MobilityOptionDemoDataSeeder implements CommandLineRunner {

    private final MobilityOptionRepository mobilityOptionRepository;

    public MobilityOptionDemoDataSeeder(MobilityOptionRepository mobilityOptionRepository) {
        this.mobilityOptionRepository = mobilityOptionRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        mobilityOptionRepository.deleteAllInBatch();

        LocalDateTime now = LocalDateTime.now();
        for (CompetitionDemoData.MobilitySeed seed : CompetitionDemoData.mobilitySeeds()) {
            mobilityOptionRepository.save(new MobilityOptionEntity(
                    seed.id() + "-bike",
                    "Bike",
                    "4 min walk",
                    "Free",
                    "Available",
                    seed.stadium() + " bike dock",
                    seed.stadium() + " north dock",
                    seed.latitude() + 0.0011,
                    seed.longitude() + 0.0011,
                    "bike",
                    now));
            mobilityOptionRepository.save(new MobilityOptionEntity(
                    seed.id() + "-scooter",
                    "Scooter",
                    "3 min walk",
                    "€1.20",
                    "Available",
                    seed.stadium() + " scooter bay",
                    seed.stadium() + " east row",
                    seed.latitude() - 0.0011,
                    seed.longitude() - 0.0011,
                    "scooter",
                    now));
        }
    }
}
