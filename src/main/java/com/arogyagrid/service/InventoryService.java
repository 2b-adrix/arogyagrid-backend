package com.arogyagrid.service;

import com.arogyagrid.dto.request.InventoryTransactionRequest;
import com.arogyagrid.dto.response.InventoryResponse;
import com.arogyagrid.entity.*;
import com.arogyagrid.enums.TransactionType;
import com.arogyagrid.exception.BadRequestException;
import com.arogyagrid.exception.ResourceNotFoundException;
import com.arogyagrid.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryBalanceRepository balanceRepo;
    private final InventoryTransactionRepository txRepo;
    private final FacilityRepository facilityRepo;
    private final MedicineRepository medicineRepo;
    private final AuditLogService auditLogService;

    public List<InventoryResponse> getBalancesByFacility(String facilityId) {
        return balanceRepo.findByFacility_FacilityId(facilityId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<InventoryResponse> getAllBalances() {
        return balanceRepo.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<InventoryResponse> getBalancesByDistrict(String district) {
        return balanceRepo.findByDistrict(district).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean recordTransaction(InventoryTransactionRequest request, 
                                      String performedBy) {
        Facility facility = facilityRepo.findById(request.getFacilityId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Facility", "id", request.getFacilityId()));

        Medicine medicine = medicineRepo.findById(request.getMedicineId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Medicine", "id", request.getMedicineId()));

        // Save transaction record
        InventoryTransaction tx = InventoryTransaction.builder()
                .facility(facility)
                .medicine(medicine)
                .type(request.getType())
                .quantity(request.getQuantity())
                .batchNumber(request.getBatchNumber())
                .remarks(request.getRemarks())
                .performedBy(performedBy)
                .build();
        txRepo.save(tx);

        // Update inventory balance
        InventoryBalance balance = balanceRepo
                .findByFacility_FacilityIdAndMedicine_MedicineId(
                        request.getFacilityId(), request.getMedicineId())
                .orElse(InventoryBalance.builder()
                        .facility(facility)
                        .medicine(medicine)
                        .closingStock(0)
                        .avgDailyConsumption(0)
                        .coverageDays(0)
                        .stockGap(0)
                        .build());

        int newStock = balance.getClosingStock();
        switch (request.getType()) {
            case RECEIPT, TRANSFER_IN, ADJUSTMENT ->
                    newStock += request.getQuantity();
            case ISSUE, TRANSFER_OUT -> {
                if (newStock < request.getQuantity()) {
                    throw new BadRequestException(
                            "Insufficient stock. Current: " + newStock 
                            + ", Requested: " + request.getQuantity());
                }
                newStock -= request.getQuantity();
            }
        }

        balance.setClosingStock(newStock);
        balance.setCoverageDays(balance.getAvgDailyConsumption() > 0
                ? (int) (newStock / balance.getAvgDailyConsumption()) : 0);
        balance.setStockGap(Math.max(0, medicine.getMinimumStock() - newStock));
        balance.setLastUpdated(LocalDateTime.now());
        balanceRepo.save(balance);

        // Audit log
        auditLogService.log("INVENTORY_TRANSACTION", "INVENTORY",
                facility.getFacilityId() + ":" + medicine.getMedicineId(),
                performedBy, null,
                String.format("%s %d units of %s at %s",
                        request.getType(), request.getQuantity(),
                        medicine.getName(), facility.getName()));

        return true;
    }

    private InventoryResponse toResponse(InventoryBalance ib) {
        return InventoryResponse.builder()
                .facilityId(ib.getFacility().getFacilityId())
                .facilityName(ib.getFacility().getName())
                .medicineId(ib.getMedicine().getMedicineId())
                .medicineName(ib.getMedicine().getName())
                .criticality(ib.getMedicine().getCriticality())
                .closingStock(ib.getClosingStock())
                .avgDailyConsumption(ib.getAvgDailyConsumption())
                .coverageDays(ib.getCoverageDays())
                .stockGap(ib.getStockGap())
                .minimumStock(ib.getMedicine().getMinimumStock())
                .maximumStock(ib.getMedicine().getMaximumStock())
                .batchNumber(ib.getBatchNumber())
                .expiryDate(ib.getExpiryDate() != null
                        ? ib.getExpiryDate().toString() : null)
                .build();
    }
}