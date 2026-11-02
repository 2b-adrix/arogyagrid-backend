package com.arogyagrid.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NearestFacilityResponse {
    private String facilityId;
    private String facilityName;
    private double latitude;
    private double longitude;
    private double distanceKm;
    private int availableStock;
    private String address;
    private String contactPhone;
}
