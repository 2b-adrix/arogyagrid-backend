package com.arogyagrid.service;

import com.arogyagrid.dto.response.BedsStaffResponse;
import com.arogyagrid.dto.response.FacilityResponse;
import com.arogyagrid.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BedsStaffService {

    private final FacilityRepository facilityRepo;
    private final FacilityService facilityService;

    public BedsStaffResponse getBedsAndStaff(String district) {
        List<FacilityResponse> facilities;
        if (district != null && !district.isBlank()) {
            facilities = facilityService.getFacilitiesByDistrict(district);
        } else {
            facilities = facilityService.getAllFacilities();
        }

        int totalBeds = facilities.stream().mapToInt(FacilityResponse::getTotalBeds).sum();
        int occupied = facilities.stream().mapToInt(FacilityResponse::getOccupiedBeds).sum();
        int available = totalBeds - occupied;
        double occupancy = totalBeds > 0
                ? Math.round((occupied * 100.0 / totalBeds) * 100.0) / 100.0 : 0;

        int totalStaff = facilities.stream().mapToInt(FacilityResponse::getTotalStaff).sum();
        int activeStaff = facilities.stream().mapToInt(FacilityResponse::getActiveStaff).sum();
        double staffRatio = totalStaff > 0
                ? Math.round((activeStaff * 1.0 / totalStaff) * 100.0) / 100.0 : 0;

        return BedsStaffResponse.builder()
                .totalBeds(totalBeds)
                .occupiedBeds(occupied)
                .availableBeds(available)
                .occupancyPercentage(occupancy)
                .totalStaff(totalStaff)
                .activeStaff(activeStaff)
                .staffingRatio(staffRatio)
                .facilities(facilities)
                .build();
    }}