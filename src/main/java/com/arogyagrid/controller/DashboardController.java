package com.arogyagrid.controller;

import com.arogyagrid.dto.response.ApiResponse;
import com.arogyagrid.dto.response.DashboardSummaryResponse;
import com.arogyagrid.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<ApiResponse<DashboardSummaryResponse>> getDashboard(
            @RequestParam(required = false) String district) {
        DashboardSummaryResponse summary = dashboardService.getDashboard(district);
        return ResponseEntity.ok(ApiResponse.ok(summary));
    }
}