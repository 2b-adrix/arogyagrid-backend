package com.arogyagrid.dto.response;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BedsStaffResponse {
    private int totalBeds;
    private int occupiedBeds;
    private int availableBeds;
    private double occupancyPercentage;
    private int totalStaff;
    private int activeStaff;
    private double staffingRatio;
    private List<FacilityResponse> facilities;
}