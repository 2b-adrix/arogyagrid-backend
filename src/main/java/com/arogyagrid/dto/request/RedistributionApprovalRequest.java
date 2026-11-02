package com.arogyagrid.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RedistributionApprovalRequest {
    @NotBlank(message = "Recommendation ID is required")
    private String recommendationId;

    @NotNull(message = "Approval decision is required")
    private Boolean approved;

    private String remarks;
}