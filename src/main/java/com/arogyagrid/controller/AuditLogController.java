package com.arogyagrid.controller;

import com.arogyagrid.dto.response.ApiResponse;
import com.arogyagrid.dto.response.AuditLogResponse;
import com.arogyagrid.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/audit-logs")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;

    @GetMapping
    @PreAuthorize("hasAnyRole('STATE_ADMIN', 'DISTRICT_OFFICER')")
    public ResponseEntity<ApiResponse<List<AuditLogResponse>>> getAll(
            @RequestParam(required = false) String performedBy,
            @RequestParam(required = false) String entityType) {
        List<AuditLogResponse> logs;
        if (performedBy != null) {
            logs = auditLogService.getLogsByUser(performedBy);
        } else if (entityType != null) {
            logs = auditLogService.getLogsByEntityType(entityType);
        } else {
            logs = auditLogService.getAllLogs();
        }
        return ResponseEntity.ok(ApiResponse.ok(logs));
    }
}