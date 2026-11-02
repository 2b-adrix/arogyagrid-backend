package com.arogyagrid.controller;

import com.arogyagrid.dto.request.RedistributionApprovalRequest;
import com.arogyagrid.dto.response.ApiResponse;
import com.arogyagrid.dto.response.RedistributionResponse;
import com.arogyagrid.service.RedistributionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/redistribution")
@RequiredArgsConstructor
public class RedistributionController {

    private final RedistributionService redistService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<RedistributionResponse>>> getAll(
            @RequestParam(required = false, defaultValue = "false") boolean pendingOnly) {
        List<RedistributionResponse> data = pendingOnly
                ? redistService.getPendingRecommendations()
                : redistService.getAllRecommendations();
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    @PostMapping("/approve")
    @PreAuthorize("hasAnyRole('STATE_ADMIN', 'DISTRICT_OFFICER')")
    public ResponseEntity<ApiResponse<RedistributionResponse>> approve(
            @Valid @RequestBody RedistributionApprovalRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        RedistributionResponse response = redistService.processApproval(
                request, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.ok("Decision recorded", response));
    }
}
