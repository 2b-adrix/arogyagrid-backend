package com.arogyagrid.service;

import com.arogyagrid.dto.response.AiChatResponse;
import com.arogyagrid.entity.*;
import com.arogyagrid.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AiAssistantService {

    private final InventoryBalanceRepository balanceRepo;
    private final RiskAssessmentRepository riskRepo;
    private final FacilityRepository facilityRepo;
    private final MedicineRepository medicineRepo;

    public AiChatResponse processQuery(String prompt) {
        String lower = prompt.toLowerCase();
        StringBuilder reply = new StringBuilder();
        List<String> dataSources = new ArrayList<>();

        // ── Medicine-related query ──
        List<Medicine> matchedMeds = medicineRepo.findAll().stream()
                .filter(m -> lower.contains(m.getName().toLowerCase())
                        || lower.contains(m.getGenericName().toLowerCase()))
                .collect(Collectors.toList());

        // ── Facility-related query ──
        List<Facility> matchedFacilities = facilityRepo.findAll().stream()
                .filter(f -> lower.contains(f.getName().toLowerCase())
                        || lower.contains(f.getDistrict().toLowerCase()))
                .collect(Collectors.toList());

        if (!matchedMeds.isEmpty() && !matchedFacilities.isEmpty()) {
            // Specific medicine at specific facility
            for (Medicine med : matchedMeds) {
                for (Facility fac : matchedFacilities) {
                    balanceRepo.findByFacility_FacilityIdAndMedicine_MedicineId(
                                    fac.getFacilityId(), med.getMedicineId())
                            .ifPresent(ib -> {
                                reply.append(String.format(
                                        "📊 **%s at %s**:\n" +
                                        "• Current Stock: %d units\n" +
                                        "• Daily Consumption: %.1f units/day\n" +
                                        "• Coverage: %d days\n" +
                                        "• Stock Gap: %d units\n\n",
                                        med.getName(), fac.getName(),
                                        ib.getClosingStock(),
                                        ib.getAvgDailyConsumption(),
                                        ib.getCoverageDays(),
                                        ib.getStockGap()));
                                dataSources.add("inventory_balances");
                            });
                }
            }
        } else if (lower.contains("risk") || lower.contains("stockout")
                || lower.contains("critical")) {
            List<RiskAssessment> risks = riskRepo.findAllByOrderByRiskScoreDesc()
                    .stream().limit(5).collect(Collectors.toList());
            reply.append("⚠️ **Top Risk Assessments:**\n\n");
            for (RiskAssessment r : risks) {
                reply.append(String.format(
                        "• [%s] %s at %s — Score: %.1f, Days to stockout: %d\n",
                        r.getSeverity(), r.getMedicine().getName(),
                        r.getFacility().getName(),
                        r.getRiskScore(), r.getDaysToStockout()));
            }
            dataSources.add("risk_assessments");
        } else if (lower.contains("bed") || lower.contains("occupancy")
                || lower.contains("capacity")) {
            List<Facility> facilities = facilityRepo.findAll();
            int totalBeds = facilities.stream().mapToInt(Facility::getTotalBeds).sum();
            int occupied = facilities.stream().mapToInt(Facility::getOccupiedBeds).sum();
            reply.append(String.format(
                    "🏥 **System Bed Status:**\n" +
                    "• Total Beds: %d\n• Occupied: %d\n• Available: %d\n" +
                    "• Occupancy: %.1f%%\n",
                    totalBeds, occupied, totalBeds - occupied,
                    totalBeds > 0 ? (occupied * 100.0 / totalBeds) : 0));
            dataSources.add("facilities");
        } else {
            reply.append("🤖 I can help with:\n" +
                    "• Medicine stock status (e.g., \"Amoxicillin at Jaipur PHC 1\")\n" +
                    "• Risk assessments (e.g., \"Show critical risks\")\n" +
                    "• Bed capacity (e.g., \"Bed occupancy status\")\n" +
                    "• Facility info (e.g., \"Facilities in Jaipur\")\n\n" +
                    "Please try a more specific query!");
            dataSources.add("system_help");
        }

        if (reply.isEmpty()) {
            reply.append("No matching data found for your query. " +
                    "Try asking about specific medicines, facilities, or risks.");
        }

        return AiChatResponse.builder()
                .reply(reply.toString())
                .dataSources(dataSources)
                .grounded(true)
                .build();
    }
}