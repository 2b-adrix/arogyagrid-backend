package com.arogyagrid.controller;

import com.arogyagrid.dto.response.ApiResponse;
import com.arogyagrid.dto.response.RiskResponse;
import com.arogyagrid.enums.RiskSeverity;
import com.arogyagrid.service.RiskAssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/risks")
@RequiredArgsConstructor
public class RiskController {

    private final RiskAssessmentService riskService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<RiskResponse>>> getAll(
            @RequestParam(required = false) RiskSeverity severity,
            @RequestParam(required = false) String facilityId,
            @RequestParam(required = false) String district) {

        List<RiskResponse> risks;
        if (severity != null) {
            risks = riskService.getRisksBySeverity(severity);
        } else if (facilityId != null) {
            risks = riskService.getRisksByFacility(facilityId);
        } else if (district != null) {
            risks = riskService.getRisksByDistrict(district);
        } else {
            risks = riskService.getAllRisks();
        }
        return ResponseEntity.ok(ApiResponse.ok(risks));
    }
}