package com.arogyagrid.service;

import com.arogyagrid.dto.response.FacilityResponse;
import com.arogyagrid.entity.Facility;
import com.arogyagrid.exception.ResourceNotFoundException;
import com.arogyagrid.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FacilityService {

    private final FacilityRepository facilityRepo;

    public List<FacilityResponse> getAllFacilities() {
        return facilityRepo.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<FacilityResponse> getFacilitiesByDistrict(String district) {
        return facilityRepo.findByDistrict(district).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public FacilityResponse getFacilityById(String facilityId) {
        Facility f = facilityRepo.findById(facilityId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Facility", "id", facilityId));
        return toResponse(f);
    }

    private FacilityResponse toResponse(Facility f) {
        int available = f.getTotalBeds() - f.getOccupiedBeds();
        double occupancy = f.getTotalBeds() > 0
                ? Math.round((f.getOccupiedBeds() * 100.0 / f.getTotalBeds()) * 100.0) / 100.0
                : 0.0;
        double staffRatio = f.getTotalStaff() > 0
                ? Math.round((f.getActiveStaff() * 1.0 / f.getTotalStaff()) * 100.0) / 100.0
                : 0.0;

        return FacilityResponse.builder()
                .facilityId(f.getFacilityId())
                .name(f.getName())
                .type(f.getType())
                .district(f.getDistrict())
                .subDistrict(f.getSubDistrict())
                .totalBeds(f.getTotalBeds())
                .occupiedBeds(f.getOccupiedBeds())
                .availableBeds(available)
                .bedOccupancyPercentage(occupancy)
                .totalStaff(f.getTotalStaff())
                .activeStaff(f.getActiveStaff())
                .staffingRatio(staffRatio)
                .latitude(f.getLatitude())
                .longitude(f.getLongitude())
                .address(f.getAddress())
                .contactPhone(f.getContactPhone())
                .build();
    }
}