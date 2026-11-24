package com.smartfan.mobility.transport.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RideRequestRepository extends JpaRepository<RideRequestEntity, UUID> {
    List<RideRequestEntity> findByPassengerEmailOrderByCreatedAtDesc(String passengerEmail);

    List<RideRequestEntity> findByMatchIdAndProviderTypeAndStatusOrderByCreatedAtDesc(String matchId, String providerType, RideRequestStatus status);

    List<RideRequestEntity> findByMatchIdAndStatusOrderByCreatedAtDesc(String matchId, RideRequestStatus status);

    List<RideRequestEntity> findByDriverProfileIdOrderByUpdatedAtDesc(String driverProfileId);
}
