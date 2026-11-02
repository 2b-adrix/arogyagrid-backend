package com.arogyagrid.controller;

import com.arogyagrid.dto.response.AlertResponse;
import com.arogyagrid.dto.response.ApiResponse;
import com.arogyagrid.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AlertResponse>>> getAll(
            @RequestParam(required = false, defaultValue = "false") boolean activeOnly) {
        List<AlertResponse> alerts = activeOnly
                ? alertService.getActiveAlerts()
                : alertService.getAllAlerts();
        return ResponseEntity.ok(ApiResponse.ok(alerts));
    }

    @PatchMapping("/{alertId}/resolve")
    public ResponseEntity<ApiResponse<AlertResponse>> resolve(
            @PathVariable String alertId,
            @AuthenticationPrincipal UserDetails userDetails) {
        AlertResponse response = alertService.resolveAlert(
                alertId, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.ok("Alert resolved", response));
    }
}