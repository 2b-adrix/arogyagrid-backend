package com.arogyagrid.dto.response;

import com.arogyagrid.enums.AlertStatus;
import com.arogyagrid.enums.RiskSeverity;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertResponse {
    private String id;
    private String title;
    private String message;
    private RiskSeverity severity;
    private AlertStatus status;
    private String facilityId;
    private String medicineId;
    private String createdAt;
}