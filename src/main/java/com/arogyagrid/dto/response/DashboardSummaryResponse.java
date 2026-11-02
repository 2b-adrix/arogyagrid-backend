package com.arogyagrid.dto.response;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSummaryResponse {
    private long totalFacilities;
    private int totalBeds;
    private int occupiedBeds;
    private double bedOccupancyPercentage;
    private long criticalRisks;
    private long highRisks;
    private long activeAlerts;
    private long pendingRedistributions;
    private List<RiskResponse> topRisks;
    private List<AlertResponse> recentAlerts;
}