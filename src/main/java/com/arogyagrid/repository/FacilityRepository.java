package com.arogyagrid.repository;

import com.arogyagrid.entity.Facility;
import com.arogyagrid.enums.FacilityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FacilityRepository extends JpaRepository<Facility, String> {

    List<Facility> findByDistrict(String district);

    List<Facility> findByType(FacilityType type);

    List<Facility> findByDistrictAndType(String district, FacilityType type);

    @Query("SELECT SUM(f.totalBeds) FROM Facility f WHERE f.active = true")
    Integer getTotalBeds();

    @Query("SELECT SUM(f.occupiedBeds) FROM Facility f WHERE f.active = true")
    Integer getTotalOccupiedBeds();

    @Query("SELECT SUM(f.totalBeds) FROM Facility f WHERE f.district = :district AND f.active = true")
    Integer getTotalBedsByDistrict(String district);

    @Query("SELECT SUM(f.occupiedBeds) FROM Facility f WHERE f.district = :district AND f.active = true")
    Integer getOccupiedBedsByDistrict(String district);

    long countByActiveTrue();

    long countByDistrictAndActiveTrue(String district);
}