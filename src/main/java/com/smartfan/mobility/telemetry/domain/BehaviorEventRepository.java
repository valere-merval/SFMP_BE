package com.smartfan.mobility.telemetry.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BehaviorEventRepository extends JpaRepository<BehaviorEventEntity, String> {
    List<BehaviorEventEntity> findAllByOrderByCreatedAtDesc();
}
