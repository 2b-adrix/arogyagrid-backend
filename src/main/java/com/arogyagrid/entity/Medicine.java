package com.arogyagrid.entity;

import com.arogyagrid.enums.CriticalityLevel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medicines")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medicine {

    @Id
    @Column(name = "medicine_id", length = 50)
    private String medicineId;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 200)
    private String genericName;

    @Column(length = 100)
    private String category;

    @Column(length = 50)
    private String unit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CriticalityLevel criticality;

    @Column(nullable = false)
    private int minimumStock;

    @Column(nullable = false)
    private int maximumStock;

    @Column(nullable = false)
    private int reorderLevel;

    @Column(nullable = false)
    private int leadTimeDays;

    @Column(nullable = false)
    private double unitPrice;
}