package com.smartfan.mobility.mobility.application;

import com.smartfan.mobility.mobility.domain.MobilityOptionEntity;
import com.smartfan.mobility.mobility.domain.MobilityOptionRepository;
import com.smartfan.mobility.shared.catalogue.FixtureCatalogue;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MobilityOptionService {

    private final MobilityOptionRepository mobilityOptionRepository;

    public MobilityOptionService(MobilityOptionRepository mobilityOptionRepository) {
        this.mobilityOptionRepository = mobilityOptionRepository;
    }

    @Transactional(readOnly = true)
    public List<FixtureCatalogue.MobilityOptionView> listOptions() {
        return mobilityOptionRepository.findAllByOrderByModeAscStationAddressAsc().stream().map(this::toView).toList();
    }

    private FixtureCatalogue.MobilityOptionView toView(MobilityOptionEntity entity) {
        return new FixtureCatalogue.MobilityOptionView(
                entity.getId(),
                entity.getMode(),
                entity.getDuration(),
                entity.getPrice(),
                entity.getStatus(),
                entity.getHighlight(),
                entity.getStationAddress(),
                entity.getLatitude(),
                entity.getLongitude(),
                entity.getAssetType());
    }
}
