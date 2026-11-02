package com.arogyagrid.repository;

import com.arogyagrid.entity.RiskAssessment;
import com.arogyagrid.enums.RiskSeverity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RiskAssessmentRepository extends JpaRepository<RiskAssessment, String> {

    List<RiskAssessment> findBySeverityOrderByRiskScoreDesc(RiskSeverity severity);

    List<RiskAssessment> findByFacility_FacilityIdOrderByRiskScoreDesc(String facilityId);

    List<RiskAssessment> findByFacility_DistrictOrderByRiskScoreDesc(String district);

    List<RiskAssessment> findAllByOrderByRiskScoreDesc();

    long countBySeverity(RiskSeverity severity);
}