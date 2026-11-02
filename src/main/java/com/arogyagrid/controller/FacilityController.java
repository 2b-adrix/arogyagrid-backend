package com.arogyagrid.controller;

import com.arogyagrid.dto.response.ApiResponse;
import com.arogyagrid.dto.response.FacilityResponse;
import com.arogyagrid.service.FacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/facilities")
@RequiredArgsConstructor
public class FacilityController {

    private final FacilityService facilityService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<FacilityResponse>>> getAll(
            @RequestParam(required = false) String district) {
        List<FacilityResponse> facilities = (district != null && !district.isBlank())
                ? facilityService.getFacilitiesByDistrict(district)
                : facilityService.getAllFacilities();
        return ResponseEntity.ok(ApiResponse.ok(facilities));
    }

    @GetMapping("/{facilityId}")
    public ResponseEntity<ApiResponse<FacilityResponse>> getById(
            @PathVariable String facilityId) {
        return ResponseEntity.ok(
                ApiResponse.ok(facilityService.getFacilityById(facilityId)));
    }
}