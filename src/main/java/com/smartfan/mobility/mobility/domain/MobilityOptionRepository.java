package com.smartfan.mobility.mobility.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MobilityOptionRepository extends JpaRepository<MobilityOptionEntity, String> {
    List<MobilityOptionEntity> findAllByOrderByModeAscStationAddressAsc();
}
