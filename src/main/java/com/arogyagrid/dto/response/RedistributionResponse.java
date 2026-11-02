package com.arogyagrid.dto.response;

import com.arogyagrid.enums.RecommendationStatus;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RedistributionResponse {
    private String recommendationId;
    private String sourceFacilityId;
    private String sourceFacilityName;
    private String targetFacilityId;
    private String targetFacilityName;
    private String medicineId;
    private String medicineName;
    private int transferQuantity;
    private int sourceSurplus;
    private int targetDeficit;
    private double priorityScore;
    private RecommendationStatus status;
    private String justification;
    private String createdAt;
}