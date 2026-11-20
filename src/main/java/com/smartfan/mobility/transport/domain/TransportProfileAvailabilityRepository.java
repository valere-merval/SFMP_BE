package com.smartfan.mobility.transport.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportProfileAvailabilityRepository extends JpaRepository<TransportProfileAvailabilityEntity, String> {

    Optional<TransportProfileAvailabilityEntity> findByProfileIdAndMatchId(String profileId, String matchId);

    List<TransportProfileAvailabilityEntity> findByMatchIdAndAvailableTrueOrderByUpdatedAtDesc(String matchId);

    List<TransportProfileAvailabilityEntity> findByProfileIdOrderByUpdatedAtDesc(String profileId);
}
