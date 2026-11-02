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
            log.info("Database already seeded. Skipping...");
            return;
        }

        log.info("🌱 Seeding database...");
        seedUsers();
        seedFacilities();
        seedMedicines();
        seedInventoryBalances();
        seedRiskAssessments();
        seedAlerts();
        seedRedistributions();
        log.info("✅ Database seeding complete!");
    }

    private void seedUsers() {
        userRepo.saveAll(List.of(
            User.builder().name("Dr. Rajesh Sharma").email("admin@arogyagrid.com")
                .password(passwordEncoder.encode("admin123"))
                .role(UserRole.STATE_ADMIN).district("ALL").active(true).build(),
            User.builder().name("Dr. Priya Verma").email("district@arogyagrid.com")
                .password(passwordEncoder.encode("district123"))
                .role(UserRole.DISTRICT_OFFICER).district("Jaipur").active(true).build(),
            User.builder().name("Nurse Anita Singh").email("facility@arogyagrid.com")
                .password(passwordEncoder.encode("facility123"))
                .role(UserRole.FACILITY_MANAGER).district("Jaipur")
                .facilityId("F-1").active(true).build(),
            User.builder().name("Pharmacist Ravi Kumar").email("pharma@arogyagrid.com")
                .password(passwordEncoder.encode("pharma123"))
                .role(UserRole.PHARMACIST_OPERATOR).district("Jaipur")
                .facilityId("F-3").active(true).build()
        ));
        log.info("  → Seeded 4 users");
    }

    private void seedFacilities() {
        facilityRepo.saveAll(List.of(
            // Jaipur District
            Facility.builder().facilityId("F-1").name("Jaipur District Hospital")
                .type(FacilityType.DISTRICT_HOSPITAL).district("Jaipur")
                .subDistrict("Jaipur City").totalBeds(500).occupiedBeds(420)
                .totalStaff(150).activeStaff(135).latitude(26.9124).longitude(75.7873)
                .address("SMS Hospital Road, Jaipur").contactPhone("0141-2560291")
                .active(true).lastUpdated(LocalDateTime.now()).build(),
            Facility.builder().facilityId("F-2").name("Jaipur CHC Amber")
                .type(FacilityType.COMMUNITY_HEALTH_CENTRE).district("Jaipur")
                .subDistrict("Amber").totalBeds(100).occupiedBeds(72)
                .totalStaff(40).activeStaff(35).latitude(26.9855).longitude(75.8513)
                .address("Amber Fort Road, Jaipur").contactPhone("0141-2530100")
                .active(true).lastUpdated(LocalDateTime.now()).build(),
            Facility.builder().facilityId("F-3").name("Jaipur PHC Sanganer")
                .type(FacilityType.PRIMARY_HEALTH_CENTRE).district("Jaipur")
                .subDistrict("Sanganer").totalBeds(30).occupiedBeds(22)
                .totalStaff(12).activeStaff(10).latitude(26.8267).longitude(75.7885)
                .address("Sanganer Main Road, Jaipur").contactPhone("0141-2720050")
                .active(true).lastUpdated(LocalDateTime.now()).build(),
            Facility.builder().facilityId("F-4").name("Jaipur PHC Chaksu")
                .type(FacilityType.PRIMARY_HEALTH_CENTRE).district("Jaipur")
                .subDistrict("Chaksu").totalBeds(25).occupiedBeds(18)
                .totalStaff(10).activeStaff(8).latitude(26.6058).longitude(75.9471)
                .address("Chaksu Bus Stand, Jaipur").contactPhone("0141-2850030")
                .active(true).lastUpdated(LocalDateTime.now()).build(),
            Facility.builder().facilityId("F-5").name("Jaipur PHC Phagi")
                .type(FacilityType.PRIMARY_HEALTH_CENTRE).district("Jaipur")
                .subDistrict("Phagi").totalBeds(20).occupiedBeds(15)
                .totalStaff(8).activeStaff(7).latitude(26.5868).longitude(75.5741)
                .address("Phagi Town, Jaipur").contactPhone("0141-2870020")
                .active(true).lastUpdated(LocalDateTime.now()).build(),
            Facility.builder().facilityId("F-6").name("Jaipur CHC Chomu")
                .type(FacilityType.COMMUNITY_HEALTH_CENTRE).district("Jaipur")
                .subDistrict("Chomu").totalBeds(80).occupiedBeds(55)
                .totalStaff(35).activeStaff(30).latitude(27.1661).longitude(75.7217)
                .address("NH-11, Chomu, Jaipur").contactPhone("01423-220050")
                .active(true).lastUpdated(LocalDateTime.now()).build(),

            // Udaipur District
            Facility.builder().facilityId("F-7").name("Udaipur District Hospital")
                .type(FacilityType.DISTRICT_HOSPITAL).district("Udaipur")
                .subDistrict("Udaipur City").totalBeds(400).occupiedBeds(340)
                .totalStaff(120).activeStaff(105).latitude(24.5854).longitude(73.7125)
                .active(true).lastUpdated(LocalDateTime.now()).build(),
            Facility.builder().facilityId("F-8").name("Udaipur PHC Mavli")
                .type(FacilityType.PRIMARY_HEALTH_CENTRE).district("Udaipur")
                .subDistrict("Mavli").totalBeds(25).occupiedBeds(20)
                .totalStaff(10).activeStaff(9).latitude(24.5000).longitude(73.8500)
                .active(true).lastUpdated(LocalDateTime.now()).build(),

            // Jodhpur District
            Facility.builder().facilityId("F-9").name("Jodhpur MDM Hospital")
                .type(FacilityType.DISTRICT_HOSPITAL).district("Jodhpur")
                .subDistrict("Jodhpur City").totalBeds(600).occupiedBeds(510)
                .totalStaff(180).activeStaff(160).latitude(26.2389).longitude(73.0243)
                .active(true).lastUpdated(LocalDateTime.now()).build(),
            Facility.builder().facilityId("F-10").name("Jodhpur PHC Bilara")
                .type(FacilityType.PRIMARY_HEALTH_CENTRE).district("Jodhpur")
                .subDistrict("Bilara").totalBeds(20).occupiedBeds(14)
                .totalStaff(8).activeStaff(6).latitude(26.1779).longitude(73.7088)
                .active(true).lastUpdated(LocalDateTime.now()).build(),

            // Kota District
            Facility.builder().facilityId("F-11").name("Kota MBS Hospital")
                .type(FacilityType.DISTRICT_HOSPITAL).district("Kota")
                .subDistrict("Kota City").totalBeds(450).occupiedBeds(380)
                .totalStaff(140).activeStaff(125).latitude(25.2138).longitude(75.8648)
                .active(true).lastUpdated(LocalDateTime.now()).build(),
            Facility.builder().facilityId("F-12").name("Kota PHC Ramganj Mandi")
                .type(FacilityType.PRIMARY_HEALTH_CENTRE).district("Kota")
                .subDistrict("Ramganj Mandi").totalBeds(20).occupiedBeds(16)
                .totalStaff(8).activeStaff(7).latitude(24.6447).longitude(75.9456)
                .active(true).lastUpdated(LocalDateTime.now()).build()
        ));
        log.info("  → Seeded 12 facilities across 4 districts");
    }

    private void seedMedicines() {
        medicineRepo.saveAll(List.of(
            Medicine.builder().medicineId("M-101").name("Amoxicillin 500mg")
                .genericName("Amoxicillin").category("Antibiotic").unit("Capsules")
                .criticality(CriticalityLevel.VITAL).minimumStock(400)
                .maximumStock(2000).reorderLevel(500).leadTimeDays(7)
                .unitPrice(2.50).build(),
            Medicine.builder().medicineId("M-102").name("ORS Sachets")
                .genericName("Oral Rehydration Salts").category("Rehydration")
                .unit("Sachets").criticality(CriticalityLevel.VITAL)
                .minimumStock(500).maximumStock(3000).reorderLevel(600)
                .leadTimeDays(5).unitPrice(1.00).build(),
            Medicine.builder().medicineId("M-103").name("Paracetamol 500mg")
                .genericName("Paracetamol").category("Analgesic").unit("Tablets")
                .criticality(CriticalityLevel.ESSENTIAL).minimumStock(1000)
                .maximumStock(5000).reorderLevel(1200).leadTimeDays(4)
                .unitPrice(0.50).build(),
            Medicine.builder().medicineId("M-104").name("Metformin 500mg")
                .genericName("Metformin").category("Antidiabetic").unit("Tablets")
                .criticality(CriticalityLevel.ESSENTIAL).minimumStock(300)
                .maximumStock(1500).reorderLevel(400).leadTimeDays(6)
                .unitPrice(1.80).build(),
            Medicine.builder().medicineId("M-105").name("Amlodipine 5mg")
                .genericName("Amlodipine").category("Antihypertensive")
                .unit("Tablets").criticality(CriticalityLevel.ESSENTIAL)
                .minimumStock(200).maximumStock(1000).reorderLevel(300)
                .leadTimeDays(5).unitPrice(1.20).build(),
            Medicine.builder().medicineId("M-106").name("Chloroquine Phosphate")
                .genericName("Chloroquine").category("Antimalarial")
                .unit("Tablets").criticality(CriticalityLevel.VITAL)
                .minimumStock(200).maximumStock(800).reorderLevel(250)
                .leadTimeDays(10).unitPrice(3.00).build()
        ));
        log.info("  → Seeded 6 medicines");
    }

    private void seedInventoryBalances() {
        Facility f1 = facilityRepo.findById("F-1").orElseThrow();
        Facility f3 = facilityRepo.findById("F-3").orElseThrow();
        Facility f7 = facilityRepo.findById("F-7").orElseThrow();
        Facility f9 = facilityRepo.findById("F-9").orElseThrow();

        Medicine m101 = medicineRepo.findById("M-101").orElseThrow();
        Medicine m102 = medicineRepo.findById("M-102").orElseThrow();
        Medicine m103 = medicineRepo.findById("M-103").orElseThrow();
        Medicine m104 = medicineRepo.findById("M-104").orElseThrow();

        balanceRepo.saveAll(List.of(
            InventoryBalance.builder().facility(f1).medicine(m101)
                .closingStock(1800).avgDailyConsumption(25).coverageDays(72)
                .stockGap(0).batchNumber("B-2025-001")
                .lastUpdated(LocalDateTime.now()).build(),
            InventoryBalance.builder().facility(f3).medicine(m101)
                .closingStock(70).avgDailyConsumption(15).coverageDays(4)
                .stockGap(330).batchNumber("B-2025-002")
                .lastUpdated(LocalDateTime.now()).build(),
            InventoryBalance.builder().facility(f3).medicine(m102)
                .closingStock(50).avgDailyConsumption(20).coverageDays(2)
                .stockGap(450).batchNumber("B-2025-003")
                .lastUpdated(LocalDateTime.now()).build(),
            InventoryBalance.builder().facility(f1).medicine(m102)
                .closingStock(2500).avgDailyConsumption(30).coverageDays(83)
                .stockGap(0).batchNumber("B-2025-004")
                .lastUpdated(LocalDateTime.now()).build(),
            InventoryBalance.builder().facility(f7).medicine(m103)
                .closingStock(3500).avgDailyConsumption(50).coverageDays(70)
                .stockGap(0).batchNumber("B-2025-005")
                .lastUpdated(LocalDateTime.now()).build(),
            InventoryBalance.builder().facility(f9).medicine(m104)
                .closingStock(100).avgDailyConsumption(18).coverageDays(5)
                .stockGap(200).batchNumber("B-2025-006")
                .lastUpdated(LocalDateTime.now()).build()
        ));
        log.info("  → Seeded 6 inventory balances");
    }

    private void seedRiskAssessments() {
        Facility f3 = facilityRepo.findById("F-3").orElseThrow();
        Facility f9 = facilityRepo.findById("F-9").orElseThrow();
        Facility f10 = facilityRepo.findById("F-10").orElseThrow();

        Medicine m101 = medicineRepo.findById("M-101").orElseThrow();
        Medicine m102 = medicineRepo.findById("M-102").orElseThrow();
        Medicine m104 = medicineRepo.findById("M-104").orElseThrow();
        Medicine m106 = medicineRepo.findById("M-106").orElseThrow();

        riskRepo.saveAll(List.of(
            RiskAssessment.builder().facility(f3).medicine(m101)
                .severity(RiskSeverity.CRITICAL).riskScore(92.5)
                .daysToStockout(4).currentStock(70).requiredStock(400)
                .deficit(330).recommendation("Urgent transfer from Jaipur DH")
                .assessedAt(LocalDateTime.now()).build(),
            RiskAssessment.builder().facility(f3).medicine(m102)
                .severity(RiskSeverity.CRITICAL).riskScore(96.0)
                .daysToStockout(2).currentStock(50).requiredStock(500)
                .deficit(450).recommendation("Emergency ORS replenishment needed")
                .assessedAt(LocalDateTime.now()).build(),
            RiskAssessment.builder().facility(f9).medicine(m104)
                .severity(RiskSeverity.HIGH).riskScore(78.0)
                .daysToStockout(5).currentStock(100).requiredStock(300)
                .deficit(200).recommendation("Reorder from state warehouse")
                .assessedAt(LocalDateTime.now()).build(),
            RiskAssessment.builder().facility(f10).medicine(m106)
                .severity(RiskSeverity.MEDIUM).riskScore(55.0)
                .daysToStockout(12).currentStock(80).requiredStock(200)
                .deficit(120).recommendation("Monitor closely during monsoon")
                .assessedAt(LocalDateTime.now()).build()
        ));
        log.info("  → Seeded 4 risk assessments");
    }

    private void seedAlerts() {
        alertRepo.saveAll(List.of(
            Alert.builder().title("CRITICAL: Amoxicillin stockout imminent at Sanganer PHC")
                .message("Jaipur PHC Sanganer has only 70 units of Amoxicillin 500mg remaining with 4 days to stockout.")
                .severity(RiskSeverity.CRITICAL).status(AlertStatus.ACTIVE)
                .facilityId("F-3").medicineId("M-101").build(),
            Alert.builder().title("CRITICAL: ORS depleting at Sanganer PHC")
                .message("Only 50 ORS sachets remaining at Sanganer PHC. 2 days to stockout.")
                .severity(RiskSeverity.CRITICAL).status(AlertStatus.ACTIVE)
                .facilityId("F-3").medicineId("M-102").build(),
            Alert.builder().title("HIGH: Metformin running low at Jodhpur MDM")
                .message("Jodhpur MDM Hospital has 100 units of Metformin with 5 days coverage.")
                .severity(RiskSeverity.HIGH).status(AlertStatus.ACTIVE)
                .facilityId("F-9").medicineId("M-104").build(),
            Alert.builder().title("Bed occupancy above 85% at Jaipur DH")
                .message("Jaipur District Hospital at 84% bed occupancy. Consider patient diversion plan.")
                .severity(RiskSeverity.MEDIUM).status(AlertStatus.ACKNOWLEDGED)
                .facilityId("F-1").build()
        ));
        log.info("  → Seeded 4 alerts");
    }

    private void seedRedistributions() {
        Facility f1 = facilityRepo.findById("F-1").orElseThrow();
        Facility f3 = facilityRepo.findById("F-3").orElseThrow();
        Medicine m101 = medicineRepo.findById("M-101").orElseThrow();
        Medicine m102 = medicineRepo.findById("M-102").orElseThrow();

        redistRepo.saveAll(List.of(
            RedistributionRecommendation.builder()
                .recommendationId("REC-2026-001")
                .sourceFacility(f1).targetFacility(f3).medicine(m101)
                .transferQuantity(330).sourceSurplus(1400).targetDeficit(330)
                .priorityScore(92.5).status(RecommendationStatus.PENDING_APPROVAL)
                .justification("Critical stockout risk at PHC Sanganer. DH has 1400 surplus units.")
                .build(),
            RedistributionRecommendation.builder()
                .recommendationId("REC-2026-002")
                .sourceFacility(f1).targetFacility(f3).medicine(m102)
                .transferQuantity(450).sourceSurplus(2000).targetDeficit(450)
                .priorityScore(96.0).status(RecommendationStatus.PENDING_APPROVAL)
                .justification("Emergency ORS transfer needed. PHC Sanganer has only 2 days coverage.")
                .build()
        ));
        log.info("  → Seeded 2 redistribution recommendations");
    }
}