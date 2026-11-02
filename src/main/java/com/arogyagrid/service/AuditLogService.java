package com.arogyagrid.service;

import com.arogyagrid.dto.response.AuditLogResponse;
import com.arogyagrid.entity.AuditLog;
import com.arogyagrid.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditRepo;

    public void log(String action, String entityType, String entityId,
                    String performedBy, String userRole, String details) {
        AuditLog log = AuditLog.builder()
                .action(action)
                .entityType(entityType)
                .entityId(entityId)
                .performedBy(performedBy)
                .userRole(userRole)
                .details(details)
                .build();
        auditRepo.save(log);
    }

    public List<AuditLogResponse> getAllLogs() {
        return auditRepo.findAllByOrderByTimestampDesc().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<AuditLogResponse> getLogsByUser(String performedBy) {
        return auditRepo.findByPerformedByOrderByTimestampDesc(performedBy).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<AuditLogResponse> getLogsByEntityType(String entityType) {
        return auditRepo.findByEntityTypeOrderByTimestampDesc(entityType).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private AuditLogResponse toResponse(AuditLog a) {
        return AuditLogResponse.builder()
                .id(a.getId())
                .action(a.getAction())
                .entityType(a.getEntityType())
                .entityId(a.getEntityId())
                .performedBy(a.getPerformedBy())
                .userRole(a.getUserRole())
                .details(a.getDetails())
                .timestamp(a.getTimestamp() != null
                        ? a.getTimestamp().toString() : null)
                .build();
    }
}