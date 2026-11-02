package com.arogyagrid.service;

import com.arogyagrid.dto.response.AlertResponse;
import com.arogyagrid.entity.Alert;
import com.arogyagrid.enums.AlertStatus;
import com.arogyagrid.exception.ResourceNotFoundException;
import com.arogyagrid.repository.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertRepository alertRepo;
    private final AuditLogService auditLogService;

    public List<AlertResponse> getAllAlerts() {
        return alertRepo.findAllByOrderByCreatedAtDesc().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<AlertResponse> getActiveAlerts() {
        return alertRepo.findByStatusOrderByCreatedAtDesc(AlertStatus.ACTIVE).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public AlertResponse resolveAlert(String alertId, String resolvedBy) {
        Alert alert = alertRepo.findById(alertId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Alert", "id", alertId));

        alert.setStatus(AlertStatus.RESOLVED);
        alert.setResolvedBy(resolvedBy);
        alert.setResolvedAt(LocalDateTime.now());
        alert = alertRepo.save(alert);

        auditLogService.log("RESOLVE_ALERT", "ALERT", alertId,
                resolvedBy, null, "Alert resolved: " + alert.getTitle());

        return toResponse(alert);
    }

    private AlertResponse toResponse(Alert a) {
        return AlertResponse.builder()
                .id(a.getId())
                .title(a.getTitle())
                .message(a.getMessage())
                .severity(a.getSeverity())
                .status(a.getStatus())
                .facilityId(a.getFacilityId())
                .medicineId(a.getMedicineId())
                .createdAt(a.getCreatedAt() != null
                        ? a.getCreatedAt().toString() : null)
                .build();
    }
}