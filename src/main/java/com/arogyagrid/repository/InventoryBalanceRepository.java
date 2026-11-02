package com.arogyagrid.repository;

import com.arogyagrid.entity.InventoryBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InventoryBalanceRepository extends JpaRepository<InventoryBalance, String> {

    List<InventoryBalance> findByFacility_FacilityId(String facilityId);

    Optional<InventoryBalance> findByFacility_FacilityIdAndMedicine_MedicineId(
            String facilityId, String medicineId);

    @Query("SELECT ib FROM InventoryBalance ib WHERE ib.stockGap > 0")
    List<InventoryBalance> findAllWithDeficit();

    @Query("SELECT ib FROM InventoryBalance ib WHERE ib.closingStock > ib.medicine.maximumStock")
    List<InventoryBalance> findAllWithSurplus();

    @Query("SELECT ib FROM InventoryBalance ib WHERE ib.facility.district = :district")
    List<InventoryBalance> findByDistrict(String district);
}