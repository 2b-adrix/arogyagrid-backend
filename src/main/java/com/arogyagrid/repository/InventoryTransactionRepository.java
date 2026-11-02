package com.arogyagrid.repository;

import com.arogyagrid.entity.InventoryTransaction;
import com.arogyagrid.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface InventoryTransactionRepository extends JpaRepository<InventoryTransaction, String> {

    List<InventoryTransaction> findByFacility_FacilityIdOrderByTransactionDateDesc(String facilityId);

    List<InventoryTransaction> findByFacility_FacilityIdAndMedicine_MedicineIdOrderByTransactionDateDesc(
            String facilityId, String medicineId);

    List<InventoryTransaction> findByTypeOrderByTransactionDateDesc(TransactionType type);

    List<InventoryTransaction> findByTransactionDateBetweenOrderByTransactionDateDesc(
            LocalDateTime start, LocalDateTime end);
}