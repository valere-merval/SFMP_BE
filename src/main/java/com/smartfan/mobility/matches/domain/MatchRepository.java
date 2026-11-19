package com.smartfan.mobility.matches.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<MatchEntity, String> {
    List<MatchEntity> findAllByOrderByDateAscTimeAsc();
    List<MatchEntity> findByCatalogIdOrderByDateAscTimeAsc(String catalogId);
}
