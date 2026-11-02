package com.arogyagrid.config;

import com.arogyagrid.entity.*;
import com.arogyagrid.enums.*;
import com.arogyagrid.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepo;
    private final FacilityRepository facilityRepo;
    private final MedicineRepository medicineRepo;
    private final InventoryBalanceRepository balanceRepo;
    private final RiskAssessmentRepository riskRepo;
    private final AlertRepository alertRepo;
    private final RedistributionRepository redistRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepo.count() > 0) {
            log.info("Database already populated. Skipping seeding...");
            return;
        }

        log.info("🌱 Seeding Jaipur Multi-Hospital Health Grid Dataset...");
        seedUsers();
        seedFacilities();
        seedMedicines();
        seedInventoryBalances();
        seedRiskAssessments();
        seedAlerts();
        seedRedistributions();
        log.info("✅ Jaipur 5-Hospital Health Grid Database Seeding Complete!");
    }

    private void seedUsers() {
        userRepo.saveAll(List.of(
            User.builder().name("Dr. Rajesh Sharma").email("admin@arogyagrid.com")
                .password(passwordEncoder.encode("admin123"))
                .role(UserRole.STATE_ADMIN).district("ALL").active(true).build(),
            User.builder().name("Dr. Priya Verma").email("district@arogyagrid.com")
                .password(passwordEncoder.encode("district123"))
                .role(UserRole.DISTRICT_OFFICER).district("Jaipur").active(true).build(),
            User.builder().name("Dr. Ashok Gehlot").email("sms.manager@arogyagrid.com")
                .password(passwordEncoder.encode("facility123"))
                .role(UserRole.FACILITY_MANAGER).district("Jaipur")
                .facilityId("FAC-JPR-001").active(true).build(),
            User.builder().name("Pharmacist Suresh Kumar").email("amer.pharma@arogyagrid.com")
                .password(passwordEncoder.encode("pharma123"))
                .role(UserRole.PHARMACIST_OPERATOR).district("Jaipur")
                .facilityId("FAC-JPR-005").active(true).build(),
            User.builder().name("Pharmacist Anita Meena").email("pharma@arogyagrid.com")
                .password(passwordEncoder.encode("pharma123"))
                .role(UserRole.PHARMACIST_OPERATOR).district("Jaipur")
                .facilityId("FAC-JPR-004").active(true).build(),
            User.builder().name("Nurse Sunita Rao").email("facility@arogyagrid.com")
                .password(passwordEncoder.encode("facility123"))
                .role(UserRole.FACILITY_MANAGER).district("Jaipur")
                .facilityId("FAC-JPR-001").active(true).build()
        ));
        log.info("  → Seeded 6 system users across roles");
    }

    private void seedFacilities() {
        facilityRepo.saveAll(List.of(
            // 1. SMS Hospital Jaipur
            Facility.builder().facilityId("FAC-JPR-001")
                .name("Sawai ManSingh (SMS) Hospital, Jaipur")
                .type(FacilityType.DISTRICT_HOSPITAL).district("Jaipur")
                .subDistrict("Jaipur Central").totalBeds(1500).occupiedBeds(1280)
                .totalStaff(450).activeStaff(390).latitude(26.8924).longitude(75.8173)
                .address("JLN Marg, Jaipur, Rajasthan 302004")
                .contactPhone("0141-2560291").active(true)
                .lastUpdated(LocalDateTime.now()).build(),

            // 2. JK Lon Hospital Jaipur
            Facility.builder().facilityId("FAC-JPR-002")
                .name("JK Lon Hospital, Jaipur")
                .type(FacilityType.DISTRICT_HOSPITAL).district("Jaipur")
                .subDistrict("Jaipur Central").totalBeds(800).occupiedBeds(690)
                .totalStaff(220).activeStaff(195).latitude(26.8950).longitude(75.8150)
                .address("JLN Marg, Near SMS Medical College, Jaipur 302004")
                .contactPhone("0141-2564246").active(true)
                .lastUpdated(LocalDateTime.now()).build(),

            // 3. Zanana Hospital Jaipur
            Facility.builder().facilityId("FAC-JPR-003")
                .name("Zanana Hospital, Jaipur")
                .type(FacilityType.DISTRICT_HOSPITAL).district("Jaipur")
                .subDistrict("Chandpole").totalBeds(600).occupiedBeds(510)
                .totalStaff(180).activeStaff(160).latitude(26.9260).longitude(75.8120)
                .address("Station Road, Chandpole, Jaipur 302001")
                .contactPhone("0141-2373022").active(true)
                .lastUpdated(LocalDateTime.now()).build(),

            // 4. CHC Sanganer
            Facility.builder().facilityId("FAC-JPR-004")
                .name("CHC Sanganer, Jaipur")
                .type(FacilityType.COMMUNITY_HEALTH_CENTRE).district("Jaipur")
                .subDistrict("Sanganer").totalBeds(100).occupiedBeds(82)
                .totalStaff(45).activeStaff(38).latitude(26.8267).longitude(75.7885)
                .address("Tonk Road, Sanganer, Jaipur 302029")
                .contactPhone("0141-2720050").active(true)
                .lastUpdated(LocalDateTime.now()).build(),

            // 5. PHC Amer
            Facility.builder().facilityId("FAC-JPR-005")
                .name("PHC Amer, Jaipur")
                .type(FacilityType.PRIMARY_HEALTH_CENTRE).district("Jaipur")
                .subDistrict("Amer").totalBeds(30).occupiedBeds(24)
                .totalStaff(15).activeStaff(12).latitude(26.9855).longitude(75.8513)
                .address("Fort Road, Amer, Jaipur 302028")
                .contactPhone("0141-2530100").active(true)
                .lastUpdated(LocalDateTime.now()).build()
        ));
        log.info("  → Seeded 5 Jaipur Hospitals & Health Centers");
    }

    private void seedMedicines() {
        medicineRepo.saveAll(List.of(
            // Antibiotics
            Medicine.builder().medicineId("MED-101").name("Amoxicillin 500mg")
                .genericName("Amoxicillin").category("Antibiotics").unit("Capsules")
                .criticality(CriticalityLevel.VITAL).minimumStock(400)
                .maximumStock(3000).reorderLevel(600).leadTimeDays(5)
                .unitPrice(2.50).build(),
            Medicine.builder().medicineId("MED-102").name("Azithromycin 500mg")
                .genericName("Azithromycin").category("Antibiotics").unit("Tablets")
                .criticality(CriticalityLevel.ESSENTIAL).minimumStock(300)
                .maximumStock(2000).reorderLevel(500).leadTimeDays(6)
                .unitPrice(8.00).build(),
            Medicine.builder().medicineId("MED-103").name("Ciprofloxacin 500mg")
                .genericName("Ciprofloxacin").category("Antibiotics").unit("Tablets")
                .criticality(CriticalityLevel.ESSENTIAL).minimumStock(250)
                .maximumStock(1500).reorderLevel(400).leadTimeDays(5)
                .unitPrice(3.50).build(),
            Medicine.builder().medicineId("MED-104").name("Ceftriaxone 1g Inj")
                .genericName("Ceftriaxone Injection").category("Antibiotics").unit("Vials")
                .criticality(CriticalityLevel.VITAL).minimumStock(200)
                .maximumStock(1200).reorderLevel(350).leadTimeDays(4)
                .unitPrice(22.00).build(),

            // Emergency & Critical
            Medicine.builder().medicineId("MED-201").name("Medical Oxygen Cylinder (D-Type)")
                .genericName("Compressed Oxygen").category("Emergency & Critical").unit("Cylinders")
                .criticality(CriticalityLevel.VITAL).minimumStock(50)
                .maximumStock(300).reorderLevel(80).leadTimeDays(2)
                .unitPrice(250.00).build(),
            Medicine.builder().medicineId("MED-202").name("ORS Sachets")
                .genericName("Oral Rehydration Salts").category("Emergency & Critical").unit("Sachets")
                .criticality(CriticalityLevel.VITAL).minimumStock(500)
                .maximumStock(5000).reorderLevel(800).leadTimeDays(3)
                .unitPrice(1.00).build(),
            Medicine.builder().medicineId("MED-203").name("IV Dextrose 5% 500ml")
                .genericName("Dextrose Fluid").category("Emergency & Critical").unit("Bottles")
                .criticality(CriticalityLevel.VITAL).minimumStock(300)
                .maximumStock(2000).reorderLevel(450).leadTimeDays(3)
                .unitPrice(18.50).build(),
            Medicine.builder().medicineId("MED-204").name("Inj Adrenaline 1mg/ml")
                .genericName("Epinephrine Injection").category("Emergency & Critical").unit("Ampoules")
                .criticality(CriticalityLevel.VITAL).minimumStock(100)
                .maximumStock(800).reorderLevel(200).leadTimeDays(2)
                .unitPrice(12.00).build(),

            // Chronic / Daily
            Medicine.builder().medicineId("MED-301").name("Metformin 500mg")
                .genericName("Metformin").category("Chronic Care").unit("Tablets")
                .criticality(CriticalityLevel.ESSENTIAL).minimumStock(500)
                .maximumStock(4000).reorderLevel(800).leadTimeDays(7)
                .unitPrice(1.80).build(),
            Medicine.builder().medicineId("MED-302").name("Amlodipine 5mg")
                .genericName("Amlodipine").category("Chronic Care").unit("Tablets")
                .criticality(CriticalityLevel.ESSENTIAL).minimumStock(400)
                .maximumStock(3000).reorderLevel(600).leadTimeDays(5)
                .unitPrice(1.20).build(),
            Medicine.builder().medicineId("MED-303").name("Atorvastatin 10mg")
                .genericName("Atorvastatin").category("Chronic Care").unit("Tablets")
                .criticality(CriticalityLevel.ESSENTIAL).minimumStock(300)
                .maximumStock(2500).reorderLevel(500).leadTimeDays(6)
                .unitPrice(3.20).build(),
            Medicine.builder().medicineId("MED-304").name("Paracetamol 650mg")
                .genericName("Paracetamol").category("Chronic Care").unit("Tablets")
                .criticality(CriticalityLevel.VITAL).minimumStock(1000)
                .maximumStock(8000).reorderLevel(1500).leadTimeDays(3)
                .unitPrice(0.60).build(),

            // Pediatric & Vaccines
            Medicine.builder().medicineId("MED-401").name("Paracetamol Syrup 125mg/5ml")
                .genericName("Pediatric Paracetamol").category("Pediatric & Vaccines").unit("Bottles")
                .criticality(CriticalityLevel.ESSENTIAL).minimumStock(150)
                .maximumStock(1000).reorderLevel(250).leadTimeDays(4)
                .unitPrice(15.00).build(),
            Medicine.builder().medicineId("MED-402").name("BCG Vaccine")
                .genericName("Tuberculosis Vaccine").category("Pediatric & Vaccines").unit("Vials")
                .criticality(CriticalityLevel.VITAL).minimumStock(100)
                .maximumStock(600).reorderLevel(150).leadTimeDays(7)
                .unitPrice(45.00).build(),
            Medicine.builder().medicineId("MED-403").name("Zinc Tablets 20mg")
                .genericName("Zinc Supplement").category("Pediatric & Vaccines").unit("Tablets")
                .criticality(CriticalityLevel.ESSENTIAL).minimumStock(300)
                .maximumStock(2000).reorderLevel(450).leadTimeDays(5)
                .unitPrice(0.90).build(),
            Medicine.builder().medicineId("MED-404").name("Rotavirus Vaccine")
                .genericName("Oral Rotavirus Drops").category("Pediatric & Vaccines").unit("Doses")
                .criticality(CriticalityLevel.VITAL).minimumStock(80)
                .maximumStock(500).reorderLevel(120).leadTimeDays(8)
                .unitPrice(85.00).build()
        ));
        log.info("  → Seeded 16 Essential Medicines across 4 medical categories");
    }

    private void seedInventoryBalances() {
        Facility sms = facilityRepo.findById("FAC-JPR-001").orElseThrow();
        Facility jkl = facilityRepo.findById("FAC-JPR-002").orElseThrow();
        Facility zan = facilityRepo.findById("FAC-JPR-003").orElseThrow();
        Facility chc = facilityRepo.findById("FAC-JPR-004").orElseThrow();
        Facility phc = facilityRepo.findById("FAC-JPR-005").orElseThrow();

        Medicine m101 = medicineRepo.findById("MED-101").orElseThrow();
        Medicine m104 = medicineRepo.findById("MED-104").orElseThrow();
        Medicine m201 = medicineRepo.findById("MED-201").orElseThrow();
        Medicine m202 = medicineRepo.findById("MED-202").orElseThrow();
        Medicine m301 = medicineRepo.findById("MED-301").orElseThrow();
        Medicine m304 = medicineRepo.findById("MED-304").orElseThrow();
        Medicine m401 = medicineRepo.findById("MED-401").orElseThrow();

        balanceRepo.saveAll(List.of(
            // SMS Hospital (Large Surplus Center)
            InventoryBalance.builder().facility(sms).medicine(m101)
                .closingStock(2800).avgDailyConsumption(45.0).coverageDays(62)
                .stockGap(0).batchNumber("B-SMS-2026-A1")
                .lastUpdated(LocalDateTime.now()).build(),
            InventoryBalance.builder().facility(sms).medicine(m201)
                .closingStock(220).avgDailyConsumption(8.0).coverageDays(27)
                .stockGap(0).batchNumber("B-SMS-OXY-01")
                .lastUpdated(LocalDateTime.now()).build(),
            InventoryBalance.builder().facility(sms).medicine(m202)
                .closingStock(4500).avgDailyConsumption(60.0).coverageDays(75)
                .stockGap(0).batchNumber("B-SMS-ORS-99")
                .lastUpdated(LocalDateTime.now()).build(),
            InventoryBalance.builder().facility(sms).medicine(m304)
                .closingStock(7500).avgDailyConsumption(120.0).coverageDays(62)
                .stockGap(0).batchNumber("B-SMS-PCM-02")
                .lastUpdated(LocalDateTime.now()).build(),

            // JK Lon Hospital (Pediatric Specialty)
            InventoryBalance.builder().facility(jkl).medicine(m401)
                .closingStock(850).avgDailyConsumption(25.0).coverageDays(34)
                .stockGap(0).batchNumber("B-JKL-PED-11")
                .lastUpdated(LocalDateTime.now()).build(),
            InventoryBalance.builder().facility(jkl).medicine(m202)
                .closingStock(2200).avgDailyConsumption(35.0).coverageDays(62)
                .stockGap(0).batchNumber("B-JKL-ORS-05")
                .lastUpdated(LocalDateTime.now()).build(),

            // Zanana Hospital (Maternal Center)
            InventoryBalance.builder().facility(zan).medicine(m104)
                .closingStock(150).avgDailyConsumption(22.0).coverageDays(6)
                .stockGap(200).batchNumber("B-ZAN-CEF-08")
                .lastUpdated(LocalDateTime.now()).build(),

            // CHC Sanganer (Deficit on Oxygen)
            InventoryBalance.builder().facility(chc).medicine(m201)
                .closingStock(12).avgDailyConsumption(3.5).coverageDays(3)
                .stockGap(38).batchNumber("B-CHC-OXY-02")
                .lastUpdated(LocalDateTime.now()).build(),
            InventoryBalance.builder().facility(chc).medicine(m301)
                .closingStock(350).avgDailyConsumption(20.0).coverageDays(17)
                .stockGap(150).batchNumber("B-CHC-MET-04")
                .lastUpdated(LocalDateTime.now()).build(),

            // PHC Amer (Critical Shortages)
            InventoryBalance.builder().facility(phc).medicine(m202)
                .closingStock(40).avgDailyConsumption(20.0).coverageDays(2)
                .stockGap(460).batchNumber("B-AMR-ORS-01")
                .lastUpdated(LocalDateTime.now()).build(),
            InventoryBalance.builder().facility(phc).medicine(m101)
                .closingStock(50).avgDailyConsumption(16.0).coverageDays(3)
                .stockGap(350).batchNumber("B-AMR-AMX-02")
                .lastUpdated(LocalDateTime.now()).build()
        ));
        log.info("  → Seeded inventory balances for all 5 hospitals");
    }

    private void seedRiskAssessments() {
        Facility sms = facilityRepo.findById("FAC-JPR-001").orElseThrow();
        Facility chc = facilityRepo.findById("FAC-JPR-004").orElseThrow();
        Facility phc = facilityRepo.findById("FAC-JPR-005").orElseThrow();
        Facility zan = facilityRepo.findById("FAC-JPR-003").orElseThrow();

        Medicine m101 = medicineRepo.findById("MED-101").orElseThrow();
        Medicine m104 = medicineRepo.findById("MED-104").orElseThrow();
        Medicine m201 = medicineRepo.findById("MED-201").orElseThrow();
        Medicine m202 = medicineRepo.findById("MED-202").orElseThrow();

        riskRepo.saveAll(List.of(
            RiskAssessment.builder().facility(phc).medicine(m202)
                .severity(RiskSeverity.CRITICAL).riskScore(96.5)
                .daysToStockout(2).currentStock(40).requiredStock(500)
                .deficit(460).recommendation("Emergency ORS transfer needed from SMS Hospital")
                .assessedAt(LocalDateTime.now()).build(),

            RiskAssessment.builder().facility(phc).medicine(m101)
                .severity(RiskSeverity.CRITICAL).riskScore(93.0)
                .daysToStockout(3).currentStock(50).requiredStock(400)
                .deficit(350).recommendation("Urgent Amoxicillin transfer from SMS Hospital")
                .assessedAt(LocalDateTime.now()).build(),

            RiskAssessment.builder().facility(chc).medicine(m201)
                .severity(RiskSeverity.HIGH).riskScore(82.0)
                .daysToStockout(3).currentStock(12).requiredStock(50)
                .deficit(38).recommendation("Oxygen Cylinder supply request dispatched")
                .assessedAt(LocalDateTime.now()).build(),

            RiskAssessment.builder().facility(zan).medicine(m104)
                .severity(RiskSeverity.MEDIUM).riskScore(64.0)
                .daysToStockout(6).currentStock(150).requiredStock(350)
                .deficit(200).recommendation("Reorder Ceftriaxone Vials from state warehouse")
                .assessedAt(LocalDateTime.now()).build()
        ));
        log.info("  → Seeded 4 stockout risk assessments");
    }

    private void seedAlerts() {
        alertRepo.saveAll(List.of(
            Alert.builder().title("CRITICAL: Emergency ORS shortage at PHC Amer")
                .message("PHC Amer, Jaipur has only 40 ORS sachets remaining with 2 days to stockout.")
                .severity(RiskSeverity.CRITICAL).status(AlertStatus.ACTIVE)
                .facilityId("FAC-JPR-005").medicineId("MED-202").build(),

            Alert.builder().title("CRITICAL: Amoxicillin depleting at PHC Amer")
                .message("PHC Amer has 50 capsules of Amoxicillin 500mg. 3 days coverage remaining.")
                .severity(RiskSeverity.CRITICAL).status(AlertStatus.ACTIVE)
                .facilityId("FAC-JPR-005").medicineId("MED-101").build(),

            Alert.builder().title("HIGH: Oxygen Cylinders running low at CHC Sanganer")
                .message("CHC Sanganer has only 12 Oxygen Cylinders remaining. 3 days until deficit.")
                .severity(RiskSeverity.HIGH).status(AlertStatus.ACTIVE)
                .facilityId("FAC-JPR-004").medicineId("MED-201").build(),

            Alert.builder().title("ICU Bed Occupancy at 85% in SMS Hospital")
                .message("SMS Hospital ICU ward is at 85% capacity. Patient diversion plan advised.")
                .severity(RiskSeverity.MEDIUM).status(AlertStatus.ACKNOWLEDGED)
                .facilityId("FAC-JPR-001").build()
        ));
        log.info("  → Seeded 4 active alerts");
    }

    private void seedRedistributions() {
        Facility sms = facilityRepo.findById("FAC-JPR-001").orElseThrow();
        Facility chc = facilityRepo.findById("FAC-JPR-004").orElseThrow();
        Facility phc = facilityRepo.findById("FAC-JPR-005").orElseThrow();

        Medicine m101 = medicineRepo.findById("MED-101").orElseThrow();
        Medicine m201 = medicineRepo.findById("MED-201").orElseThrow();
        Medicine m202 = medicineRepo.findById("MED-202").orElseThrow();

        redistRepo.saveAll(List.of(
            RedistributionRecommendation.builder()
                .recommendationId("REC-JPR-2026-01")
                .sourceFacility(sms).targetFacility(phc).medicine(m202)
                .transferQuantity(460).sourceSurplus(4500).targetDeficit(460)
                .priorityScore(96.5).status(RecommendationStatus.PENDING_APPROVAL)
                .justification("Emergency ORS transfer from SMS Hospital (4500 surplus) to PHC Amer (2 days coverage).")
                .build(),

            RedistributionRecommendation.builder()
                .recommendationId("REC-JPR-2026-02")
                .sourceFacility(sms).targetFacility(phc).medicine(m101)
                .transferQuantity(350).sourceSurplus(2800).targetDeficit(350)
                .priorityScore(93.0).status(RecommendationStatus.PENDING_APPROVAL)
                .justification("Urgent Amoxicillin transfer from SMS Hospital to PHC Amer.")
                .build(),

            RedistributionRecommendation.builder()
                .recommendationId("REC-JPR-2026-03")
                .sourceFacility(sms).targetFacility(chc).medicine(m201)
                .transferQuantity(25).sourceSurplus(220).targetDeficit(25)
                .priorityScore(82.0).status(RecommendationStatus.PENDING_APPROVAL)
                .justification("Medical Oxygen transfer from SMS Hospital (220 cylinders) to CHC Sanganer.")
                .build()
        ));
        log.info("  → Seeded 3 inter-hospital redistribution recommendations");
    }
}
