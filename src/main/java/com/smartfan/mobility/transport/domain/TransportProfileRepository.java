package com.smartfan.mobility.transport.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportProfileRepository extends JpaRepository<TransportProfileEntity, String> {

    Optional<TransportProfileEntity> findByOwnerEmail(String ownerEmail);

    List<TransportProfileEntity> findAllByOrderByUpdatedAtDesc();
}
