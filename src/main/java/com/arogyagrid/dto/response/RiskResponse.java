package com.arogyagrid.dto.response;

import com.arogyagrid.enums.RiskSeverity;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskResponse {
    private String id;
    private String facilityId;
    private String facilityName;
    private String medicineId;
    private String medicineName;
    private RiskSeverity severity;
    private double riskScore;
    private int daysToStockout;
    private int currentStock;
    private int requiredStock;
    private int deficit;
    private String recommendation;
    private String assessedAt;
}