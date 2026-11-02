package com.arogyagrid.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogResponse {
    private String id;
    private String action;
    private String entityType;
    private String entityId;
    private String performedBy;
    private String userRole;
    private String details;
    private String timestamp;
}