package com.arogyagrid.service;

import com.arogyagrid.dto.request.RedistributionApprovalRequest;
import com.arogyagrid.dto.response.RedistributionResponse;
import com.arogyagrid.entity.RedistributionRecommendation;
import com.arogyagrid.enums.RecommendationStatus;
import com.arogyagrid.exception.BadRequestException;
import com.arogyagrid.exception.ResourceNotFoundException;
import com.arogyagrid.repository.RedistributionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RedistributionService {

    private final RedistributionRepository redistRepo;
    private final AuditLogService auditLogService;

    public List<RedistributionResponse> getAllRecommendations() {
        return redistRepo.findAllByOrderByPriorityScoreDesc().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<RedistributionResponse> getPendingRecommendations() {
        return redistRepo.findByStatusOrderByPriorityScoreDesc(
                        RecommendationStatus.PENDING_APPROVAL).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public RedistributionResponse processApproval(
            RedistributionApprovalRequest request, String approvedBy) {

        RedistributionRecommendation rec = redistRepo
                .findById(request.getRecommendationId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Redistribution", "id", request.getRecommendationId()));

        if (rec.getStatus() != RecommendationStatus.PENDING_APPROVAL) {
            throw new BadRequestException(
                    "Recommendation is not in PENDING_APPROVAL status");
        }

        rec.setStatus(request.getApproved()
                ? RecommendationStatus.APPROVED
                : RecommendationStatus.REJECTED);
        rec.setApprovedBy(approvedBy);
        rec.setApprovedAt(LocalDateTime.now());
        rec = redistRepo.save(rec);

        auditLogService.log(
                request.getApproved() ? "APPROVE_REDISTRIBUTION" : "REJECT_REDISTRIBUTION",
                "REDISTRIBUTION",
                rec.getRecommendationId(),
                approvedBy, null,
                String.format("Redistribution %s: %d units of %s from %s to %s",
                        rec.getStatus(),
                        rec.getTransferQuantity(),
                        rec.getMedicine().getName(),
                        rec.getSourceFacility().getName(),
                        rec.getTargetFacility().getName()));

        return toResponse(rec);
    }

    private RedistributionResponse toResponse(RedistributionRecommendation r) {
        return RedistributionResponse.builder()
                .recommendationId(r.getRecommendationId())
                .sourceFacilityId(r.getSourceFacility().getFacilityId())
                .sourceFacilityName(r.getSourceFacility().getName())
                .targetFacilityId(r.getTargetFacility().getFacilityId())
                .targetFacilityName(r.getTargetFacility().getName())
                .medicineId(r.getMedicine().getMedicineId())
                .medicineName(r.getMedicine().getName())
                .transferQuantity(r.getTransferQuantity())
                .sourceSurplus(r.getSourceSurplus())
                .targetDeficit(r.getTargetDeficit())
                .priorityScore(r.getPriorityScore())
                .status(r.getStatus())
                .justification(r.getJustification())
                .createdAt(r.getCreatedAt() != null
                        ? r.getCreatedAt().toString() : null)
                .build();
    }
}