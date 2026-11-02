package com.arogyagrid.service;

import com.arogyagrid.dto.response.RiskResponse;
import com.arogyagrid.entity.RiskAssessment;
import com.arogyagrid.enums.RiskSeverity;
import com.arogyagrid.repository.RiskAssessmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RiskAssessmentService {

    private final RiskAssessmentRepository riskRepo;

    public List<RiskResponse> getAllRisks() {
        return riskRepo.findAllByOrderByRiskScoreDesc().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<RiskResponse> getRisksBySeverity(RiskSeverity severity) {
        return riskRepo.findBySeverityOrderByRiskScoreDesc(severity).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<RiskResponse> getRisksByFacility(String facilityId) {
        return riskRepo.findByFacility_FacilityIdOrderByRiskScoreDesc(facilityId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<RiskResponse> getRisksByDistrict(String district) {
        return riskRepo.findByFacility_DistrictOrderByRiskScoreDesc(district)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private RiskResponse toResponse(RiskAssessment r) {
        return RiskResponse.builder()
                .id(r.getId())
                .facilityId(r.getFacility().getFacilityId())
                .facilityName(r.getFacility().getName())
                .medicineId(r.getMedicine().getMedicineId())
                .medicineName(r.getMedicine().getName())
                .severity(r.getSeverity())
                .riskScore(r.getRiskScore())
                .daysToStockout(r.getDaysToStockout())
                .currentStock(r.getCurrentStock())
                .requiredStock(r.getRequiredStock())
                .deficit(r.getDeficit())
                .recommendation(r.getRecommendation())
                .assessedAt(r.getAssessedAt() != null
                        ? r.getAssessedAt().toString() : null)
                .build();
    }
}