package com.smartfan.mobility.catalog.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventCatalogRepository extends JpaRepository<EventCatalogEntity, String> {
    List<EventCatalogEntity> findAllByOrderByTitleAsc();
}
