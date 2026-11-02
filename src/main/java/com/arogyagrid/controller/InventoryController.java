package com.arogyagrid.controller;

import com.arogyagrid.dto.request.InventoryTransactionRequest;
import com.arogyagrid.dto.response.ApiResponse;
import com.arogyagrid.dto.response.InventoryResponse;
import com.arogyagrid.dto.response.NearestFacilityResponse;
import com.arogyagrid.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<InventoryResponse>>> getAll(
            @RequestParam(required = false) String facilityId,
            @RequestParam(required = false) String district) {
        List<InventoryResponse> data;
        if (facilityId != null && !facilityId.isBlank()) {
            data = inventoryService.getBalancesByFacility(facilityId);
        } else if (district != null && !district.isBlank()) {
            data = inventoryService.getBalancesByDistrict(district);
        } else {
            data = inventoryService.getAllBalances();
        }
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    @GetMapping("/nearest")
    public ResponseEntity<ApiResponse<List<NearestFacilityResponse>>> getNearestSurplus(
            @RequestParam String facilityId,
            @RequestParam String medicineId) {
        List<NearestFacilityResponse> data = inventoryService.getNearestSurplusFacilities(facilityId, medicineId);
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    @PostMapping("/transaction")
    public ResponseEntity<ApiResponse<Boolean>> recordTransaction(
            @Valid @RequestBody InventoryTransactionRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        boolean result = inventoryService.recordTransaction(
                request, userDetails.getUsername());
        return ResponseEntity.ok(
                ApiResponse.ok("Transaction recorded successfully", result));
    }
}
