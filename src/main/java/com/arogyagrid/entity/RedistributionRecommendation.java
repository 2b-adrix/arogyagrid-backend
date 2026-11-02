package com.arogyagrid.entity;

import com.arogyagrid.enums.RecommendationStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "redistribution_recommendations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RedistributionRecommendation {

    @Id
    @Column(name = "recommendation_id", length = 50)
    private String recommendationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_facility_id", nullable = false)
    private Facility sourceFacility;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_facility_id", nullable = false)
    private Facility targetFacility;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;

    @Column(nullable = false)
    private int transferQuantity;

    @Column(nullable = false)
    private int sourceSurplus;

    @Column(nullable = false)
    private int targetDeficit;

    @Column(nullable = false)
    private double priorityScore;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private RecommendationStatus status;

    @Column(name = "approved_by", length = 100)
    private String approvedBy;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(length = 500)
    private String justification;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}