package com.arogyagrid.service;

import com.arogyagrid.dto.response.*;
import com.arogyagrid.enums.AlertStatus;
import com.arogyagrid.enums.RecommendationStatus;
import com.arogyagrid.enums.RiskSeverity;
import com.arogyagrid.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final FacilityRepository facilityRepo;
    private final RiskAssessmentRepository riskRepo;
    private final AlertRepository alertRepo;
    private final RedistributionRepository redistRepo;
    private final RiskAssessmentService riskService;
    private final AlertService alertService;

    public DashboardSummaryResponse getDashboard(String district) {
        long totalFacilities;
        int totalBeds, occupiedBeds;

        if (district != null && !district.isBlank()) {
            totalFacilities = facilityRepo.countByDistrictAndActiveTrue(district);
            totalBeds = safeInt(facilityRepo.getTotalBedsByDistrict(district));
            occupiedBeds = safeInt(facilityRepo.getOccupiedBedsByDistrict(district));
        } else {
            totalFacilities = facilityRepo.countByActiveTrue();
            totalBeds = safeInt(facilityRepo.getTotalBeds());
            occupiedBeds = safeInt(facilityRepo.getTotalOccupiedBeds());
        }

        double occupancy = totalBeds > 0 
                ? Math.round((occupiedBeds * 100.0 / totalBeds) * 100.0) / 100.0 
                : 0.0;

        long criticalRisks = riskRepo.countBySeverity(RiskSeverity.CRITICAL);
        long highRisks = riskRepo.countBySeverity(RiskSeverity.HIGH);
        long activeAlerts = alertRepo.countByStatus(AlertStatus.ACTIVE);
        long pendingRedist = redistRepo.countByStatus(RecommendationStatus.PENDING_APPROVAL);

        List<RiskResponse> topRisks = riskService.getAllRisks().stream()
                .limit(5)
                .collect(Collectors.toList());

        List<AlertResponse> recentAlerts = alertService.getAllAlerts().stream()
                .limit(5)
                .collect(Collectors.toList());

        return DashboardSummaryResponse.builder()
                .totalFacilities(totalFacilities)
                .totalBeds(totalBeds)
                .occupiedBeds(occupiedBeds)
                .bedOccupancyPercentage(occupancy)
                .criticalRisks(criticalRisks)
                .highRisks(highRisks)
                .activeAlerts(activeAlerts)
                .pendingRedistributions(pendingRedist)
                .topRisks(topRisks)
                .recentAlerts(recentAlerts)
                .build();
    }

    private int safeInt(Integer value) {
        return value != null ? value : 0;
    }
}