package com.arogyagrid.dto.request;

import com.arogyagrid.enums.TransactionType;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class InventoryTransactionRequest {
    @NotBlank(message = "Facility ID is required")
    private String facilityId;

    @NotBlank(message = "Medicine ID is required")
    private String medicineId;

    @NotNull(message = "Transaction type is required")
    private TransactionType type;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    private String batchNumber;
    private String remarks;
}