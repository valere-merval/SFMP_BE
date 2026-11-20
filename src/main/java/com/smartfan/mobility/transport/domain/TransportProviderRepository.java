package com.smartfan.mobility.transport.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportProviderRepository extends JpaRepository<TransportProviderEntity, String> {

    List<TransportProviderEntity> findAllByOrderByCreatedAtDesc();

    List<TransportProviderEntity> findByMatchIdOrderByCreatedAtDesc(String matchId);
}
