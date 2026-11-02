package com.arogyagrid.entity;

import com.arogyagrid.enums.FacilityType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "facilities")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Facility {

    @Id
    @Column(name = "facility_id", length = 50)
    private String facilityId;

    @Column(nullable = false, length = 200)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private FacilityType type;

    @Column(nullable = false, length = 100)
    private String district;

    @Column(length = 100)
    private String subDistrict;

    @Column(nullable = false)
    private int totalBeds;

    @Column(nullable = false)
    private int occupiedBeds;

    @Column(nullable = false)
    private int totalStaff;

    @Column(nullable = false)
    private int activeStaff;

    private double latitude;

    private double longitude;

    @Column(length = 500)
    private String address;

    @Column(length = 20)
    private String contactPhone;

    @Column(nullable = false)
    private boolean active = true;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
}