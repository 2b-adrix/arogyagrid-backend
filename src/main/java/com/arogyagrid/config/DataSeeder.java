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
import java.util.ArrayList;
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
        log.info("🌱 Refreshing & Seeding Comprehensive Jaipur Multi-Hospital Health Grid Dataset...");
        try {
            redistRepo.deleteAll();
            alertRepo.deleteAll();
            riskRepo.deleteAll();
            balanceRepo.deleteAll();
            medicineRepo.deleteAll();
            userRepo.deleteAll();
            facilityRepo.deleteAll();
        } catch (Exception e) {
            log.warn("Non-fatal error clearing tables prior to seeding: {}", e.getMessage());
        }

        seedUsers();
        seedFacilities();
        seedMedicines();
        seedInventoryBalances();
        seedRiskAssessments();
        seedAlerts();
        seedRedistributions();
        log.info("✅ Comprehensive Jaipur Health Grid Database Seeding Complete!");
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
                .facilityId("FAC-JPR-011").active(true).build(),
            User.builder().name("Pharmacist Anita Meena").email("pharma@arogyagrid.com")
                .password(passwordEncoder.encode("pharma123"))
                .role(UserRole.PHARMACIST_OPERATOR).district("Jaipur")
                .facilityId("FAC-JPR-006").active(true).build(),
            User.builder().name("Nurse Sunita Rao").email("facility@arogyagrid.com")
                .password(passwordEncoder.encode("facility123"))
                .role(UserRole.FACILITY_MANAGER).district("Jaipur")
                .facilityId("FAC-JPR-001").active(true).build()
        ));
        log.info("  → Seeded 6 system users across roles");
    }

    private void seedFacilities() {
        facilityRepo.saveAll(List.of(
            // Tertiary & Specialty Hospitals
            Facility.builder().facilityId("FAC-JPR-001")
                .name("Sawai ManSingh (SMS) Hospital, Jaipur")
                .type(FacilityType.DISTRICT_HOSPITAL).district("Jaipur")
                .subDistrict("Jaipur Central").totalBeds(1500).occupiedBeds(1280)
                .totalStaff(450).activeStaff(390).latitude(26.8924).longitude(75.8173)
                .address("JLN Marg, Jaipur, Rajasthan 302004").contactPhone("0141-2560291")
                .active(true).lastUpdated(LocalDateTime.now()).build(),

            Facility.builder().facilityId("FAC-JPR-002")
                .name("JK Lon Pediatric Hospital, Jaipur")
                .type(FacilityType.DISTRICT_HOSPITAL).district("Jaipur")
                .subDistrict("Jaipur Central").totalBeds(800).occupiedBeds(690)
                .totalStaff(220).activeStaff(195).latitude(26.8950).longitude(75.8150)
                .address("JLN Marg, Near SMS Medical College, Jaipur 302004").contactPhone("0141-2564246")
                .active(true).lastUpdated(LocalDateTime.now()).build(),

            Facility.builder().facilityId("FAC-JPR-003")
                .name("Zanana Maternity Hospital, Jaipur")
                .type(FacilityType.DISTRICT_HOSPITAL).district("Jaipur")
                .subDistrict("Chandpole").totalBeds(600).occupiedBeds(510)
                .totalStaff(180).activeStaff(160).latitude(26.9260).longitude(75.8120)
                .address("Station Road, Chandpole, Jaipur 302001").contactPhone("0141-2373022")
                .active(true).lastUpdated(LocalDateTime.now()).build(),

            Facility.builder().facilityId("FAC-JPR-004")
                .name("Mahila Chikitsalaya, Jaipur")
                .type(FacilityType.DISTRICT_HOSPITAL).district("Jaipur")
                .subDistrict("Sanganeri Gate").totalBeds(500).occupiedBeds(430)
                .totalStaff(160).activeStaff(145).latitude(26.9150).longitude(75.8200)
                .address("Sanganeri Gate, Jaipur 302003").contactPhone("0141-2601240")
                .active(true).lastUpdated(LocalDateTime.now()).build(),

            Facility.builder().facilityId("FAC-JPR-005")
                .name("RUHS College of Medical Sciences & Hospital, Jaipur")
                .type(FacilityType.DISTRICT_HOSPITAL).district("Jaipur")
                .subDistrict("Pratap Nagar").totalBeds(750).occupiedBeds(610)
                .totalStaff(210).activeStaff(185).latitude(26.8020).longitude(75.8100)
                .address("Sector 11, Pratap Nagar, Jaipur 302033").contactPhone("0141-2791928")
                .active(true).lastUpdated(LocalDateTime.now()).build(),

            // Community Health Centres (CHCs)
            Facility.builder().facilityId("FAC-JPR-006")
                .name("CHC Sanganer, Jaipur")
                .type(FacilityType.COMMUNITY_HEALTH_CENTRE).district("Jaipur")
                .subDistrict("Sanganer").totalBeds(100).occupiedBeds(82)
                .totalStaff(45).activeStaff(38).latitude(26.8267).longitude(75.7885)
                .address("Tonk Road, Sanganer, Jaipur 302029").contactPhone("0141-2720050")
                .active(true).lastUpdated(LocalDateTime.now()).build(),

            Facility.builder().facilityId("FAC-JPR-007")
                .name("CHC Chomu, Jaipur")
                .type(FacilityType.COMMUNITY_HEALTH_CENTRE).district("Jaipur")
                .subDistrict("Chomu").totalBeds(100).occupiedBeds(78)
                .totalStaff(40).activeStaff(35).latitude(27.1661).longitude(75.7217)
                .address("NH-11, Chomu, Jaipur 303702").contactPhone("01423-220050")
                .active(true).lastUpdated(LocalDateTime.now()).build(),

            Facility.builder().facilityId("FAC-JPR-008")
                .name("CHC Amber, Jaipur")
                .type(FacilityType.COMMUNITY_HEALTH_CENTRE).district("Jaipur")
                .subDistrict("Amber").totalBeds(80).occupiedBeds(62)
                .totalStaff(35).activeStaff(30).latitude(26.9880).longitude(75.8550)
                .address("Amber Fort Road, Jaipur 302028").contactPhone("0141-2530200")
                .active(true).lastUpdated(LocalDateTime.now()).build(),

            Facility.builder().facilityId("FAC-JPR-009")
                .name("CHC Kotputli, Jaipur")
                .type(FacilityType.COMMUNITY_HEALTH_CENTRE).district("Jaipur")
                .subDistrict("Kotputli").totalBeds(120).occupiedBeds(95)
                .totalStaff(50).activeStaff(42).latitude(27.7020).longitude(76.2010)
                .address("NH-48, Kotputli, Jaipur 303108").contactPhone("01421-222030")
                .active(true).lastUpdated(LocalDateTime.now()).build(),

            Facility.builder().facilityId("FAC-JPR-010")
                .name("CHC Shahpura, Jaipur")
                .type(FacilityType.COMMUNITY_HEALTH_CENTRE).district("Jaipur")
                .subDistrict("Shahpura").totalBeds(90).occupiedBeds(70)
                .totalStaff(38).activeStaff(32).latitude(27.3850).longitude(75.9620)
                .address("Delhi Road, Shahpura, Jaipur 303103").contactPhone("01422-225010")
                .active(true).lastUpdated(LocalDateTime.now()).build(),

            // Primary Health Centres (PHCs)
            Facility.builder().facilityId("FAC-JPR-011")
                .name("PHC Amer, Jaipur")
                .type(FacilityType.PRIMARY_HEALTH_CENTRE).district("Jaipur")
                .subDistrict("Amer").totalBeds(30).occupiedBeds(24)
                .totalStaff(15).activeStaff(12).latitude(26.9855).longitude(75.8513)
                .address("Fort Road, Amer, Jaipur 302028").contactPhone("0141-2530100")
                .active(true).lastUpdated(LocalDateTime.now()).build(),

            Facility.builder().facilityId("FAC-JPR-012")
                .name("PHC Chaksu, Jaipur")
                .type(FacilityType.PRIMARY_HEALTH_CENTRE).district("Jaipur")
                .subDistrict("Chaksu").totalBeds(25).occupiedBeds(18)
                .totalStaff(12).activeStaff(10).latitude(26.6058).longitude(75.9471)
                .address("Chaksu Bus Stand, Jaipur 303901").contactPhone("0141-2850030")
                .active(true).lastUpdated(LocalDateTime.now()).build(),

            Facility.builder().facilityId("FAC-JPR-013")
                .name("PHC Phagi, Jaipur")
                .type(FacilityType.PRIMARY_HEALTH_CENTRE).district("Jaipur")
                .subDistrict("Phagi").totalBeds(20).occupiedBeds(15)
                .totalStaff(10).activeStaff(8).latitude(26.5868).longitude(75.5741)
                .address("Phagi Town, Jaipur 303005").contactPhone("0141-2870020")
                .active(true).lastUpdated(LocalDateTime.now()).build(),

            Facility.builder().facilityId("FAC-JPR-014")
                .name("PHC Bassi, Jaipur")
                .type(FacilityType.PRIMARY_HEALTH_CENTRE).district("Jaipur")
                .subDistrict("Bassi").totalBeds(25).occupiedBeds(20)
                .totalStaff(12).activeStaff(10).latitude(26.8340).longitude(76.0420)
                .address("Bassi Main Market, Jaipur 303301").contactPhone("01429-222050")
                .active(true).lastUpdated(LocalDateTime.now()).build(),

            Facility.builder().facilityId("FAC-JPR-015")
                .name("PHC Jamwa Ramgarh, Jaipur")
                .type(FacilityType.PRIMARY_HEALTH_CENTRE).district("Jaipur")
                .subDistrict("Jamwa Ramgarh").totalBeds(20).occupiedBeds(16)
                .totalStaff(10).activeStaff(8).latitude(27.0250).longitude(76.0120)
                .address("Ramgarh Dam Road, Jaipur 303109").contactPhone("01426-224010")
                .active(true).lastUpdated(LocalDateTime.now()).build()
        ));
        log.info("  → Seeded 15 Jaipur Hospitals, CHCs, and PHCs");
    }

    private void seedMedicines() {
        medicineRepo.saveAll(List.of(
            // Antibiotics & Anti-Infectives
            Medicine.builder().medicineId("MED-101").name("Amoxicillin 500mg")
                .genericName("Amoxicillin").category("Antibiotics").unit("Capsules")
                .criticality(CriticalityLevel.VITAL).minimumStock(400)
                .maximumStock(3000).reorderLevel(600).leadTimeDays(5).unitPrice(2.50).build(),

            Medicine.builder().medicineId("MED-102").name("Azithromycin 500mg")
                .genericName("Azithromycin").category("Antibiotics").unit("Tablets")
                .criticality(CriticalityLevel.ESSENTIAL).minimumStock(300)
                .maximumStock(2000).reorderLevel(500).leadTimeDays(6).unitPrice(8.00).build(),

            Medicine.builder().medicineId("MED-103").name("Ciprofloxacin 500mg")
                .genericName("Ciprofloxacin").category("Antibiotics").unit("Tablets")
                .criticality(CriticalityLevel.ESSENTIAL).minimumStock(250)
                .maximumStock(1500).reorderLevel(400).leadTimeDays(5).unitPrice(3.50).build(),

            Medicine.builder().medicineId("MED-104").name("Ceftriaxone 1g Inj")
                .genericName("Ceftriaxone Injection").category("Antibiotics").unit("Vials")
                .criticality(CriticalityLevel.VITAL).minimumStock(200)
                .maximumStock(1200).reorderLevel(350).leadTimeDays(4).unitPrice(22.00).build(),

            Medicine.builder().medicineId("MED-105").name("Doxycycline 100mg")
                .genericName("Doxycycline").category("Antibiotics").unit("Capsules")
                .criticality(CriticalityLevel.ESSENTIAL).minimumStock(200)
                .maximumStock(1000).reorderLevel(300).leadTimeDays(5).unitPrice(1.80).build(),

            Medicine.builder().medicineId("MED-106").name("Meropenem 1g Inj")
                .genericName("Meropenem Injection").category("Antibiotics").unit("Vials")
                .criticality(CriticalityLevel.VITAL).minimumStock(50)
                .maximumStock(400).reorderLevel(100).leadTimeDays(3).unitPrice(180.00).build(),

            // Emergency & Critical Care
            Medicine.builder().medicineId("MED-201").name("Medical Oxygen Cylinder (D-Type)")
                .genericName("Compressed Oxygen").category("Emergency & Critical").unit("Cylinders")
                .criticality(CriticalityLevel.VITAL).minimumStock(50)
                .maximumStock(300).reorderLevel(80).leadTimeDays(2).unitPrice(250.00).build(),

            Medicine.builder().medicineId("MED-202").name("ORS Sachets")
                .genericName("Oral Rehydration Salts").category("Emergency & Critical").unit("Sachets")
                .criticality(CriticalityLevel.VITAL).minimumStock(500)
                .maximumStock(5000).reorderLevel(800).leadTimeDays(3).unitPrice(1.00).build(),

            Medicine.builder().medicineId("MED-203").name("IV Dextrose 5% 500ml")
                .genericName("Dextrose Fluid").category("Emergency & Critical").unit("Bottles")
                .criticality(CriticalityLevel.VITAL).minimumStock(300)
                .maximumStock(2000).reorderLevel(450).leadTimeDays(3).unitPrice(18.50).build(),

            Medicine.builder().medicineId("MED-204").name("Inj Adrenaline 1mg/ml")
                .genericName("Epinephrine Injection").category("Emergency & Critical").unit("Ampoules")
                .criticality(CriticalityLevel.VITAL).minimumStock(100)
                .maximumStock(800).reorderLevel(200).leadTimeDays(2).unitPrice(12.00).build(),

            Medicine.builder().medicineId("MED-205").name("Normal Saline 0.9% 500ml")
                .genericName("Sodium Chloride Fluid").category("Emergency & Critical").unit("Bottles")
                .criticality(CriticalityLevel.VITAL).minimumStock(400)
                .maximumStock(3000).reorderLevel(600).leadTimeDays(3).unitPrice(16.00).build(),

            Medicine.builder().medicineId("MED-206").name("Inj Hydrocortisone 100mg")
                .genericName("Hydrocortisone Steroid").category("Emergency & Critical").unit("Vials")
                .criticality(CriticalityLevel.VITAL).minimumStock(80)
                .maximumStock(600).reorderLevel(150).leadTimeDays(3).unitPrice(28.00).build(),

            // Cardiovascular & Chronic Care
            Medicine.builder().medicineId("MED-301").name("Metformin 500mg")
                .genericName("Metformin").category("Chronic Care").unit("Tablets")
                .criticality(CriticalityLevel.ESSENTIAL).minimumStock(500)
                .maximumStock(4000).reorderLevel(800).leadTimeDays(7).unitPrice(1.80).build(),

            Medicine.builder().medicineId("MED-302").name("Amlodipine 5mg")
                .genericName("Amlodipine").category("Chronic Care").unit("Tablets")
                .criticality(CriticalityLevel.ESSENTIAL).minimumStock(400)
                .maximumStock(3000).reorderLevel(600).leadTimeDays(5).unitPrice(1.20).build(),

            Medicine.builder().medicineId("MED-303").name("Atorvastatin 10mg")
                .genericName("Atorvastatin").category("Chronic Care").unit("Tablets")
                .criticality(CriticalityLevel.ESSENTIAL).minimumStock(300)
                .maximumStock(2500).reorderLevel(500).leadTimeDays(6).unitPrice(3.20).build(),

            Medicine.builder().medicineId("MED-304").name("Paracetamol 650mg")
                .genericName("Paracetamol").category("Chronic Care").unit("Tablets")
                .criticality(CriticalityLevel.VITAL).minimumStock(1000)
                .maximumStock(8000).reorderLevel(1500).leadTimeDays(3).unitPrice(0.60).build(),

            Medicine.builder().medicineId("MED-305").name("Insulin Regular 100IU/ml")
                .genericName("Human Insulin Injection").category("Chronic Care").unit("Vials")
                .criticality(CriticalityLevel.VITAL).minimumStock(50)
                .maximumStock(400).reorderLevel(100).leadTimeDays(4).unitPrice(140.00).build(),

            Medicine.builder().medicineId("MED-306").name("Losartan Potassium 50mg")
                .genericName("Losartan").category("Chronic Care").unit("Tablets")
                .criticality(CriticalityLevel.ESSENTIAL).minimumStock(300)
                .maximumStock(2000).reorderLevel(450).leadTimeDays(5).unitPrice(2.40).build(),

            // Pediatric & Vaccines
            Medicine.builder().medicineId("MED-401").name("Paracetamol Syrup 125mg/5ml")
                .genericName("Pediatric Paracetamol").category("Pediatric & Vaccines").unit("Bottles")
                .criticality(CriticalityLevel.ESSENTIAL).minimumStock(150)
                .maximumStock(1000).reorderLevel(250).leadTimeDays(4).unitPrice(15.00).build(),

            Medicine.builder().medicineId("MED-402").name("BCG Vaccine")
                .genericName("Tuberculosis Vaccine").category("Pediatric & Vaccines").unit("Vials")
                .criticality(CriticalityLevel.VITAL).minimumStock(100)
                .maximumStock(600).reorderLevel(150).leadTimeDays(7).unitPrice(45.00).build(),

            Medicine.builder().medicineId("MED-403").name("Zinc Tablets 20mg")
                .genericName("Zinc Supplement").category("Pediatric & Vaccines").unit("Tablets")
                .criticality(CriticalityLevel.ESSENTIAL).minimumStock(300)
                .maximumStock(2000).reorderLevel(450).leadTimeDays(5).unitPrice(0.90).build(),

            Medicine.builder().medicineId("MED-404").name("Rotavirus Vaccine")
                .genericName("Oral Rotavirus Drops").category("Pediatric & Vaccines").unit("Doses")
                .criticality(CriticalityLevel.VITAL).minimumStock(80)
                .maximumStock(500).reorderLevel(120).leadTimeDays(8).unitPrice(85.00).build(),

            Medicine.builder().medicineId("MED-405").name("Pentavalent Vaccine")
                .genericName("DTP-HepB-Hib Vaccine").category("Pediatric & Vaccines").unit("Vials")
                .criticality(CriticalityLevel.VITAL).minimumStock(100)
                .maximumStock(700).reorderLevel(180).leadTimeDays(7).unitPrice(60.00).build(),

            // Analgesics & Anti-Inflammatory
            Medicine.builder().medicineId("MED-501").name("Ibuprofen 400mg")
                .genericName("Ibuprofen").category("Analgesic & NSAID").unit("Tablets")
                .criticality(CriticalityLevel.DESIRABLE).minimumStock(400)
                .maximumStock(2500).reorderLevel(600).leadTimeDays(4).unitPrice(0.80).build(),

            Medicine.builder().medicineId("MED-502").name("Diclofenac Inj 75mg/3ml")
                .genericName("Diclofenac Sodium").category("Analgesic & NSAID").unit("Ampoules")
                .criticality(CriticalityLevel.ESSENTIAL).minimumStock(150)
                .maximumStock(1000).reorderLevel(250).leadTimeDays(3).unitPrice(8.50).build(),

            // Respiratory & Gastrointestinal
            Medicine.builder().medicineId("MED-601").name("Pantoprazole 40mg")
                .genericName("Pantoprazole").category("Gastrointestinal").unit("Tablets")
                .criticality(CriticalityLevel.ESSENTIAL).minimumStock(400)
                .maximumStock(3000).reorderLevel(600).leadTimeDays(4).unitPrice(2.10).build(),

            Medicine.builder().medicineId("MED-602").name("Salbutamol Inhaler 100mcg")
                .genericName("Salbutamol Bronchodilator").category("Respiratory").unit("Inhalers")
                .criticality(CriticalityLevel.VITAL).minimumStock(60)
                .maximumStock(400).reorderLevel(100).leadTimeDays(5).unitPrice(95.00).build()
        ));
        log.info("  → Seeded 26 Essential Medicines across 6 medical categories");
    }

    private void seedInventoryBalances() {
        Facility sms = facilityRepo.findById("FAC-JPR-001").orElseThrow();
        Facility jkl = facilityRepo.findById("FAC-JPR-002").orElseThrow();
        Facility zan = facilityRepo.findById("FAC-JPR-003").orElseThrow();
        Facility mhl = facilityRepo.findById("FAC-JPR-004").orElseThrow();
        Facility ruh = facilityRepo.findById("FAC-JPR-005").orElseThrow();
        Facility chcSanganer = facilityRepo.findById("FAC-JPR-006").orElseThrow();
        Facility chcChomu = facilityRepo.findById("FAC-JPR-007").orElseThrow();
        Facility phcAmer = facilityRepo.findById("FAC-JPR-011").orElseThrow();
        Facility phcChaksu = facilityRepo.findById("FAC-JPR-012").orElseThrow();

        List<Medicine> medicines = medicineRepo.findAll();
        List<InventoryBalance> balances = new ArrayList<>();

        for (Medicine med : medicines) {
            // SMS Hospital (Major Surplus Warehouse)
            balances.add(InventoryBalance.builder().facility(sms).medicine(med)
                .closingStock(2500 + (med.getMedicineId().hashCode() % 1500))
                .avgDailyConsumption(45.0).coverageDays(55).stockGap(0)
                .batchNumber("B-SMS-2026-" + med.getMedicineId().substring(4))
                .lastUpdated(LocalDateTime.now()).build());

            // RUHS Hospital (Secondary Hub)
            balances.add(InventoryBalance.builder().facility(ruh).medicine(med)
                .closingStock(1500 + (med.getMedicineId().hashCode() % 800))
                .avgDailyConsumption(30.0).coverageDays(50).stockGap(0)
                .batchNumber("B-RUH-2026-" + med.getMedicineId().substring(4))
                .lastUpdated(LocalDateTime.now()).build());
        }

        // Specific targeted balances for risk simulation
        Medicine amx = medicineRepo.findById("MED-101").orElseThrow();
        Medicine oxy = medicineRepo.findById("MED-201").orElseThrow();
        Medicine ors = medicineRepo.findById("MED-202").orElseThrow();
        Medicine cef = medicineRepo.findById("MED-104").orElseThrow();
        Medicine met = medicineRepo.findById("MED-301").orElseThrow();
        Medicine pcmSyrup = medicineRepo.findById("MED-401").orElseThrow();
        Medicine rotavirus = medicineRepo.findById("MED-404").orElseThrow();

        balances.addAll(List.of(
            // PHC Amer Critical Shortages
            InventoryBalance.builder().facility(phcAmer).medicine(ors)
                .closingStock(35).avgDailyConsumption(18.0).coverageDays(2)
                .stockGap(465).batchNumber("B-AMR-ORS-01")
                .lastUpdated(LocalDateTime.now()).build(),

            InventoryBalance.builder().facility(phcAmer).medicine(amx)
                .closingStock(40).avgDailyConsumption(15.0).coverageDays(3)
                .stockGap(360).batchNumber("B-AMR-AMX-02")
                .lastUpdated(LocalDateTime.now()).build(),

            // CHC Sanganer Low Oxygen
            InventoryBalance.builder().facility(chcSanganer).medicine(oxy)
                .closingStock(10).avgDailyConsumption(3.0).coverageDays(3)
                .stockGap(40).batchNumber("B-CHC-OXY-02")
                .lastUpdated(LocalDateTime.now()).build(),

            InventoryBalance.builder().facility(chcSanganer).medicine(met)
                .closingStock(180).avgDailyConsumption(22.0).coverageDays(8)
                .stockGap(320).batchNumber("B-CHC-MET-05")
                .lastUpdated(LocalDateTime.now()).build(),

            // Zanana Hospital Low Ceftriaxone
            InventoryBalance.builder().facility(zan).medicine(cef)
                .closingStock(120).avgDailyConsumption(20.0).coverageDays(6)
                .stockGap(230).batchNumber("B-ZAN-CEF-09")
                .lastUpdated(LocalDateTime.now()).build(),

            // JK Lon Pediatric High Vaccine & Syrup Balances
            InventoryBalance.builder().facility(jkl).medicine(pcmSyrup)
                .closingStock(950).avgDailyConsumption(25.0).coverageDays(33)
                .stockGap(0).batchNumber("B-JKL-PED-12")
                .lastUpdated(LocalDateTime.now()).build(),

            InventoryBalance.builder().facility(jkl).medicine(rotavirus)
                .closingStock(380).avgDailyConsumption(12.0).coverageDays(31)
                .stockGap(0).batchNumber("B-JKL-ROTA-04")
                .lastUpdated(LocalDateTime.now()).build(),

            // PHC Chaksu Balances
            InventoryBalance.builder().facility(phcChaksu).medicine(amx)
                .closingStock(80).avgDailyConsumption(12.0).coverageDays(6)
                .stockGap(220).batchNumber("B-CKS-AMX-01")
                .lastUpdated(LocalDateTime.now()).build(),

            // CHC Chomu Balances
            InventoryBalance.builder().facility(chcChomu).medicine(oxy)
                .closingStock(15).avgDailyConsumption(2.5).coverageDays(6)
                .stockGap(35).batchNumber("B-CHM-OXY-03")
                .lastUpdated(LocalDateTime.now()).build()
        ));

        balanceRepo.saveAll(balances);
        log.info("  → Seeded " + balances.size() + " inventory balance records across 15 hospitals");
    }

    private void seedRiskAssessments() {
        Facility sms = facilityRepo.findById("FAC-JPR-001").orElseThrow();
        Facility chcSanganer = facilityRepo.findById("FAC-JPR-006").orElseThrow();
        Facility phcAmer = facilityRepo.findById("FAC-JPR-011").orElseThrow();
        Facility phcChaksu = facilityRepo.findById("FAC-JPR-012").orElseThrow();
        Facility zan = facilityRepo.findById("FAC-JPR-003").orElseThrow();

        Medicine amx = medicineRepo.findById("MED-101").orElseThrow();
        Medicine cef = medicineRepo.findById("MED-104").orElseThrow();
        Medicine oxy = medicineRepo.findById("MED-201").orElseThrow();
        Medicine ors = medicineRepo.findById("MED-202").orElseThrow();

        riskRepo.saveAll(List.of(
            RiskAssessment.builder().facility(phcAmer).medicine(ors)
                .severity(RiskSeverity.CRITICAL).riskScore(97.5)
                .daysToStockout(2).currentStock(35).requiredStock(500)
                .deficit(465).recommendation("Emergency ORS transfer needed from SMS Hospital")
                .assessedAt(LocalDateTime.now()).build(),

            RiskAssessment.builder().facility(phcAmer).medicine(amx)
                .severity(RiskSeverity.CRITICAL).riskScore(94.0)
                .daysToStockout(3).currentStock(40).requiredStock(400)
                .deficit(360).recommendation("Urgent Amoxicillin transfer from SMS Hospital")
                .assessedAt(LocalDateTime.now()).build(),

            RiskAssessment.builder().facility(chcSanganer).medicine(oxy)
                .severity(RiskSeverity.HIGH).riskScore(84.0)
                .daysToStockout(3).currentStock(10).requiredStock(50)
                .deficit(40).recommendation("Oxygen Cylinder transfer from RUHS Hospital")
                .assessedAt(LocalDateTime.now()).build(),

            RiskAssessment.builder().facility(zan).medicine(cef)
                .severity(RiskSeverity.MEDIUM).riskScore(68.0)
                .daysToStockout(6).currentStock(120).requiredStock(350)
                .deficit(230).recommendation("Reorder Ceftriaxone Vials from state warehouse")
                .assessedAt(LocalDateTime.now()).build(),

            RiskAssessment.builder().facility(phcChaksu).medicine(amx)
                .severity(RiskSeverity.MEDIUM).riskScore(62.0)
                .daysToStockout(6).currentStock(80).requiredStock(300)
                .deficit(220).recommendation("Monitor local consumption trends")
                .assessedAt(LocalDateTime.now()).build()
        ));
        log.info("  → Seeded 5 stockout risk assessments");
    }

    private void seedAlerts() {
        alertRepo.saveAll(List.of(
            Alert.builder().title("CRITICAL: Emergency ORS shortage at PHC Amer")
                .message("PHC Amer, Jaipur has only 35 ORS sachets remaining with 2 days to stockout.")
                .severity(RiskSeverity.CRITICAL).status(AlertStatus.ACTIVE)
                .facilityId("FAC-JPR-011").medicineId("MED-202").build(),

            Alert.builder().title("CRITICAL: Amoxicillin stockout imminent at PHC Amer")
                .message("PHC Amer has 40 capsules of Amoxicillin 500mg. 3 days coverage remaining.")
                .severity(RiskSeverity.CRITICAL).status(AlertStatus.ACTIVE)
                .facilityId("FAC-JPR-011").medicineId("MED-101").build(),

            Alert.builder().title("HIGH: Medical Oxygen Cylinders running low at CHC Sanganer")
                .message("CHC Sanganer has only 10 Oxygen Cylinders remaining. 3 days until deficit.")
                .severity(RiskSeverity.HIGH).status(AlertStatus.ACTIVE)
                .facilityId("FAC-JPR-006").medicineId("MED-201").build(),

            Alert.builder().title("ICU Bed Occupancy at 85% in SMS Hospital")
                .message("SMS Hospital ICU ward is at 85% capacity. Patient diversion plan advised.")
                .severity(RiskSeverity.MEDIUM).status(AlertStatus.ACKNOWLEDGED)
                .facilityId("FAC-JPR-001").build(),

            Alert.builder().title("HIGH: Ceftriaxone Injection supply alert at Zanana Hospital")
                .message("Zanana Hospital has 6 days of Ceftriaxone Injection coverage remaining.")
                .severity(RiskSeverity.HIGH).status(AlertStatus.ACTIVE)
                .facilityId("FAC-JPR-003").medicineId("MED-104").build()
        ));
        log.info("  → Seeded 5 active alerts");
    }

    private void seedRedistributions() {
        Facility sms = facilityRepo.findById("FAC-JPR-001").orElseThrow();
        Facility ruh = facilityRepo.findById("FAC-JPR-005").orElseThrow();
        Facility chcSanganer = facilityRepo.findById("FAC-JPR-006").orElseThrow();
        Facility phcAmer = facilityRepo.findById("FAC-JPR-011").orElseThrow();
        Facility phcChaksu = facilityRepo.findById("FAC-JPR-012").orElseThrow();

        Medicine amx = medicineRepo.findById("MED-101").orElseThrow();
        Medicine oxy = medicineRepo.findById("MED-201").orElseThrow();
        Medicine ors = medicineRepo.findById("MED-202").orElseThrow();

        redistRepo.saveAll(List.of(
            RedistributionRecommendation.builder()
                .recommendationId("REC-JPR-2026-01")
                .sourceFacility(sms).targetFacility(phcAmer).medicine(ors)
                .transferQuantity(465).sourceSurplus(4500).targetDeficit(465)
                .priorityScore(97.5).status(RecommendationStatus.PENDING_APPROVAL)
                .justification("Emergency ORS transfer from SMS Hospital (4500 surplus) to PHC Amer (2 days coverage).")
                .build(),

            RedistributionRecommendation.builder()
                .recommendationId("REC-JPR-2026-02")
                .sourceFacility(sms).targetFacility(phcAmer).medicine(amx)
                .transferQuantity(360).sourceSurplus(2800).targetDeficit(360)
                .priorityScore(94.0).status(RecommendationStatus.PENDING_APPROVAL)
                .justification("Urgent Amoxicillin transfer from SMS Hospital to PHC Amer.")
                .build(),

            RedistributionRecommendation.builder()
                .recommendationId("REC-JPR-2026-03")
                .sourceFacility(ruh).targetFacility(chcSanganer).medicine(oxy)
                .transferQuantity(30).sourceSurplus(180).targetDeficit(30)
                .priorityScore(84.0).status(RecommendationStatus.PENDING_APPROVAL)
                .justification("Medical Oxygen transfer from RUHS Hospital (180 cylinders) to CHC Sanganer.")
                .build(),

            RedistributionRecommendation.builder()
                .recommendationId("REC-JPR-2026-04")
                .sourceFacility(sms).targetFacility(phcChaksu).medicine(amx)
                .transferQuantity(220).sourceSurplus(2800).targetDeficit(220)
                .priorityScore(62.0).status(RecommendationStatus.PENDING_APPROVAL)
                .justification("Preventative Amoxicillin transfer from SMS Hospital to PHC Chaksu.")
                .build()
        ));
        log.info("  → Seeded 4 inter-hospital redistribution recommendations");
    }
}
