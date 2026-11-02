package com.arogyagrid.entity;

import com.arogyagrid.enums.RiskSeverity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "risk_assessments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiskAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id", nullable = false)
    private Facility facility;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private RiskSeverity severity;

    @Column(nullable = false)
    private double riskScore;

    @Column(nullable = false)
    private int daysToStockout;

    @Column(nullable = false)
    private int currentStock;

    @Column(nullable = false)
    private int requiredStock;

    @Column(nullable = false)
    private int deficit;

    @Column(length = 500)
    private String recommendation;

    @Column(name = "assessed_at")
    private LocalDateTime assessedAt;
}