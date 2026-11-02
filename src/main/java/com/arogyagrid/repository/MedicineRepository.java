package com.arogyagrid.repository;

import com.arogyagrid.entity.Medicine;
import com.arogyagrid.enums.CriticalityLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicineRepository extends JpaRepository<Medicine, String> {
    List<Medicine> findByCriticality(CriticalityLevel criticality);
    List<Medicine> findByNameContainingIgnoreCase(String name);
}