package com.arogyagrid.dto.response;

import com.arogyagrid.enums.FacilityType;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacilityResponse {
    private String facilityId;
    private String name;
    private FacilityType type;
    private String district;
    private String subDistrict;
    private int totalBeds;
    private int occupiedBeds;
    private int availableBeds;
    private double bedOccupancyPercentage;
    private int totalStaff;
    private int activeStaff;
    private double staffingRatio;
    private String address;
    private String contactPhone;
}