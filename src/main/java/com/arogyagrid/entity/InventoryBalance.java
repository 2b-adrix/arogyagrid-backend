package com.arogyagrid.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_balances",
       uniqueConstraints = @UniqueConstraint(
           columnNames = {"facility_id", "medicine_id"}
       ))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id", nullable = false)
    private Facility facility;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;

    @Column(nullable = false)
    private int closingStock;

    @Column(nullable = false)
    private double avgDailyConsumption;

    private int coverageDays;

    private int stockGap;

    @Column(length = 50)
    private String batchNumber;

    private LocalDateTime expiryDate;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
}