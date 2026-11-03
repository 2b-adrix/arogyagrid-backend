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
                .subDistrict("Jaipur West").totalBeds(600).occupiedBeds(510)
                .totalStaff(180).activeStaff(155).latitude(26.9260).longitude(75.8120)
                .address("Station Road, Sindhi Camp, Jaipur 302006").contactPhone("0141-2373221")
                .active(true).lastUpdated(LocalDateTime.now()).build(),

            Facility.builder().facilityId("FAC-JPR-004")
                .name("Mahila Chikitsalaya, Jaipur")
                .type(FacilityType.DISTRICT_HOSPITAL).district("Jaipur")
                .subDistrict("Jaipur Central").totalBeds(450).occupiedBeds(380)
                .totalStaff(140).activeStaff(120).latitude(26.9150).longitude(75.8200)
                .address("Sanganeri Gate, Johari Bazar, Jaipur 302003").contactPhone("0141-2603388")
                .active(true).lastUpdated(LocalDateTime.now()).build(),

            Facility.builder().facilityId("FAC-JPR-005")
                .name("RUHS College of Medical Sciences & Hospital, Jaipur")
                .type(FacilityType.DISTRICT_HOSPITAL).district("Jaipur")
                .subDistrict("Pratap Nagar").totalBeds(1200).occupiedBeds(980)
                .totalStaff(380).activeStaff(330).latitude(26.8020).longitude(75.8100)
                .address("Kumbha Marg, Sector 18, Pratap Nagar, Jaipur 302033").contactPhone("0141-2795555")
                .active(true).lastUpdated(LocalDateTime.now()).build(),

            // Community Health Centres (CHCs)
            Facility.builder().facilityId("FAC-JPR-006")
                .name("CHC Sanganer, Jaipur")
                .type(FacilityType.COMMUNITY_HEALTH_CENTRE).district("Jaipur")
                .subDistrict("Sanganer").totalBeds(100).occupiedBeds(82)
                .totalStaff(45).activeStaff(38).latitude(26.8267).longitude(75.7885)
                .address("Near Sanganer Stadium, Sanganer, Jaipur 302029").contactPhone("0141-2731210")
                .active(true).lastUpdated(LocalDateTime.now()).build(),

            Facility.builder().facilityId("FAC-JPR-007")
                .name("CHC Chomu, Jaipur")
                .type(FacilityType.COMMUNITY_HEALTH_CENTRE).district("Jaipur")
                .subDistrict("Chomu").totalBeds(80).occupiedBeds(65)
                .totalStaff(35).activeStaff(30).latitude(27.1661).longitude(75.7217)
                .address("Near Bus Stand, Chomu, Jaipur 303702").contactPhone("01423-220045")
                .active(true).lastUpdated(LocalDateTime.now()).build(),

            Facility.builder().facilityId("FAC-JPR-008")
                .name("CHC Amber, Jaipur")
                .type(FacilityType.COMMUNITY_HEALTH_CENTRE).district("Jaipur")
                .subDistrict("Amber").totalBeds(60).occupiedBeds(48)
                .totalStaff(28).activeStaff(24).latitude(26.9880).longitude(75.8550)
                .address("Delhi-Jaipur Highway, Amber, Jaipur 302028").contactPhone("0141-2530180")
                .active(true).lastUpdated(LocalDateTime.now()).build(),

            Facility.builder().facilityId("FAC-JPR-009")
                .name("CHC Kotputli, Jaipur")
                .type(FacilityType.COMMUNITY_HEALTH_CENTRE).district("Jaipur")
                .subDistrict("Kotputli").totalBeds(120).occupiedBeds(95)
                .totalStaff(50).activeStaff(42).latitude(27.7020).longitude(76.2010)
                .address("NH-48, Kotputli, Jaipur 303108").contactPhone("01421-222010")
                .active(true).lastUpdated(LocalDateTime.now()).build(),

            Facility.builder().facilityId("FAC-JPR-010")
                .name("CHC Shahpura, Jaipur")
                .type(FacilityType.COMMUNITY_HEALTH_CENTRE).district("Jaipur")
                .subDistrict("Shahpura").totalBeds(70).occupiedBeds(54)
                .totalStaff(30).activeStaff(26).latitude(27.3850).longitude(75.9620)
                .address("Delhi Road, Shahpura, Jaipur 303103").contactPhone("01422-225015")
                .active(true).lastUpdated(LocalDateTime.now()).build(),

            // Primary Health Centres (PHCs)
            Facility.builder().facilityId("FAC-JPR-011")
                .name("PHC Amer, Jaipur")
                .type(FacilityType.PRIMARY_HEALTH_CENTRE).district("Jaipur")
                .subDistrict("Amber").totalBeds(30).occupiedBeds(28)
                .totalStaff(15).activeStaff(12).latitude(26.9855).longitude(75.8513)
                .address("Old Amer Fort Road, Amer, Jaipur 302028").contactPhone("0141-2531020")
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
                .maximumStock(3000).reorderLevel(600).leadTimeDays(6).unitPrice(2.20).build(),

            Medicine.builder().medicineId("MED-303").name("Atorvastatin 10mg")
                .genericName("Atorvastatin").category("Chronic Care").unit("Tablets")
                .criticality(CriticalityLevel.ESSENTIAL).minimumStock(300)
                .maximumStock(2500).reorderLevel(500).leadTimeDays(6).unitPrice(5.50).build(),

            Medicine.builder().medicineId("MED-304").name("Paracetamol 650mg")
                .genericName("Paracetamol").category("Chronic Care").unit("Tablets")
                .criticality(CriticalityLevel.VITAL).minimumStock(1000)
                .maximumStock(8000).reorderLevel(1500).leadTimeDays(3).unitPrice(1.20).build(),

            Medicine.builder().medicineId("MED-305").name("Insulin Regular 100IU/ml")
                .genericName("Human Insulin").category("Chronic Care").unit("Vials")
                .criticality(CriticalityLevel.VITAL).minimumStock(50)
                .maximumStock(400).reorderLevel(100).leadTimeDays(4).unitPrice(145.00).build(),

            Medicine.builder().medicineId("MED-306").name("Losartan Potassium 50mg")
                .genericName("Losartan").category("Chronic Care").unit("Tablets")
                .criticality(CriticalityLevel.ESSENTIAL).minimumStock(300)
                .maximumStock(2000).reorderLevel(450).leadTimeDays(6).unitPrice(4.00).build(),

            // Pediatric & Maternal Health
            Medicine.builder().medicineId("MED-401").name("Paracetamol Syrup 125mg/5ml")
                .genericName("Paracetamol Pediatric").category("Pediatric Care").unit("Bottles")
                .criticality(CriticalityLevel.ESSENTIAL).minimumStock(150)
                .maximumStock(1000).reorderLevel(250).leadTimeDays(4).unitPrice(28.00).build(),

            Medicine.builder().medicineId("MED-402").name("BCG Vaccine")
                .genericName("Bacillus Calmette–Guérin").category("Immunization").unit("Doses")
                .criticality(CriticalityLevel.VITAL).minimumStock(100)
                .maximumStock(600).reorderLevel(200).leadTimeDays(7).unitPrice(45.00).build(),

            Medicine.builder().medicineId("MED-403").name("Zinc Tablets 20mg")
                .genericName("Zinc Dispersible").category("Pediatric Care").unit("Tablets")
                .criticality(CriticalityLevel.ESSENTIAL).minimumStock(300)
                .maximumStock(2000).reorderLevel(500).leadTimeDays(5).unitPrice(1.50).build(),

            Medicine.builder().medicineId("MED-404").name("Rotavirus Vaccine")
                .genericName("Rotavirus Oral").category("Immunization").unit("Vials")
                .criticality(CriticalityLevel.VITAL).minimumStock(80)
                .maximumStock(500).reorderLevel(150).leadTimeDays(7).unitPrice(180.00).build(),

            Medicine.builder().medicineId("MED-405").name("Pentavalent Vaccine")
                .genericName("DTP-HepB-Hib").category("Immunization").unit("Doses")
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
        List<Facility> facilities = facilityRepo.findAll();
        List<Medicine> medicines = medicineRepo.findAll();
        List<InventoryBalance> balances = new ArrayList<>();

        // Generate tailored, realistic inventory profiles for every single one of the 15 hospitals
        for (Facility fac : facilities) {
            String facId = fac.getFacilityId();

            for (Medicine med : medicines) {
                String medId = med.getMedicineId();
                int stock;
                double dailyCons;
                int covDays;

                if (facId.equals("FAC-JPR-001")) {
                    // SMS Hospital - State Capital Major Surplus Hub
                    stock = (int) (med.getMaximumStock() * 0.95);
                    dailyCons = 45.0;
                } else if (facId.equals("FAC-JPR-005")) {
                    // RUHS Hospital - Secondary Surplus Hub
                    stock = (int) (med.getMaximumStock() * 0.75);
                    dailyCons = 30.0;
                } else if (facId.equals("FAC-JPR-002")) {
                    // JK Lon - Pediatric Hospital
                    if (med.getCategory().contains("Pediatric") || med.getCategory().contains("Immunization")) {
                        stock = (int) (med.getMaximumStock() * 0.85);
                        dailyCons = 25.0;
                    } else {
                        stock = (int) (med.getMinimumStock() * 1.5);
                        dailyCons = 15.0;
                    }
                } else if (facId.equals("FAC-JPR-003")) {
                    // Zanana Hospital - Maternity Specialty
                    if (medId.equals("MED-104")) { // Low Ceftriaxone
                        stock = 120;
                        dailyCons = 20.0;
                    } else if (med.getCategory().contains("Emergency")) {
                        stock = (int) (med.getMaximumStock() * 0.60);
                        dailyCons = 18.0;
                    } else {
                        stock = (int) (med.getMinimumStock() * 1.2);
                        dailyCons = 12.0;
                    }
                } else if (facId.equals("FAC-JPR-011")) {
                    // PHC Amer - Simulated Crisis Facility
                    if (medId.equals("MED-202")) { // Critical ORS
                        stock = 35;
                        dailyCons = 18.0;
                    } else if (medId.equals("MED-101")) { // Critical Amoxicillin
                        stock = 40;
                        dailyCons = 15.0;
                    } else {
                        stock = (int) (med.getMinimumStock() * 0.45);
                        dailyCons = 8.0;
                    }
                } else if (facId.equals("FAC-JPR-006")) {
                    // CHC Sanganer - Oxygen & Metformin Shortage
                    if (medId.equals("MED-201")) { // Oxygen Cylinders
                        stock = 10;
                        dailyCons = 3.0;
                    } else if (medId.equals("MED-301")) { // Metformin
                        stock = 180;
                        dailyCons = 22.0;
                    } else {
                        stock = (int) (med.getMinimumStock() * 0.90);
                        dailyCons = 14.0;
                    }
                } else if (fac.getType() == FacilityType.COMMUNITY_HEALTH_CENTRE) {
                    // CHCs (Chomu, Amber, Kotputli, Shahpura)
                    stock = (int) (med.getMinimumStock() * 1.3);
                    dailyCons = 12.0;
                } else {
                    // PHCs (Chaksu, Phagi, Bassi, Jamwa Ramgarh)
                    stock = (int) (med.getMinimumStock() * 0.65);
                    dailyCons = 6.0;
                }

                covDays = dailyCons > 0 ? (int) (stock / dailyCons) : 99;
                int stockGap = Math.max(0, med.getMinimumStock() - stock);

                balances.add(InventoryBalance.builder()
                    .facility(fac)
                    .medicine(med)
                    .closingStock(stock)
                    .avgDailyConsumption(dailyCons)
                    .coverageDays(covDays)
                    .stockGap(stockGap)
                    .batchNumber("B-" + facId.substring(8) + "-2026-" + medId.substring(4))
                    .lastUpdated(LocalDateTime.now())
                    .build());
            }
        }

        balanceRepo.saveAll(balances);
        log.info("  → Seeded " + balances.size() + " complete inventory balances (26 medicines × 15 facilities)");
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
