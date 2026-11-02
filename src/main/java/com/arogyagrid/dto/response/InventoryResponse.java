package com.arogyagrid.dto.response;

import com.arogyagrid.enums.CriticalityLevel;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {
    private String facilityId;
    private String facilityName;
    private String medicineId;
    private String medicineName;
    private CriticalityLevel criticality;
    private int closingStock;
    private double avgDailyConsumption;
    private int coverageDays;
    private int stockGap;
    private int minimumStock;
    private int maximumStock;
    private String batchNumber;
    private String expiryDate;
}