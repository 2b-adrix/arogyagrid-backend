package com.arogyagrid.repository;

import com.arogyagrid.entity.Alert;
import com.arogyagrid.enums.AlertStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, String> {

    List<Alert> findByStatusOrderByCreatedAtDesc(AlertStatus status);

    List<Alert> findByFacilityIdOrderByCreatedAtDesc(String facilityId);

    List<Alert> findAllByOrderByCreatedAtDesc();

    long countByStatus(AlertStatus status);
}