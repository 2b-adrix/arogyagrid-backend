package com.arogyagrid.service;

import com.arogyagrid.dto.response.ForecastResponse;
import com.arogyagrid.entity.InventoryBalance;
import com.arogyagrid.repository.InventoryBalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ForecastService {

    private final InventoryBalanceRepository balanceRepo;

    public List<ForecastResponse> getForecast(String facilityId) {
        List<InventoryBalance> balances;
        if (facilityId != null && !facilityId.isBlank()) {
            balances = balanceRepo.findByFacility_FacilityId(facilityId);
        } else {
            balances = balanceRepo.findAll();
        }

        return balances.stream()
                .map(this::buildForecast)
                .collect(Collectors.toList());
    }

    private ForecastResponse buildForecast(InventoryBalance ib) {
        double currentConsumption = ib.getAvgDailyConsumption();
        // Simple trend: +5% predicted increase
        double predictedConsumption = Math.round(currentConsumption * 1.05 * 100.0) / 100.0;
        int predictedDays = predictedConsumption > 0
                ? (int) (ib.getClosingStock() / predictedConsumption) : 999;

        // Weekly forecast (4 weeks)
        List<Double> weeklyForecast = new ArrayList<>();
        double remaining = ib.getClosingStock();
        for (int w = 0; w < 4; w++) {
            remaining -= predictedConsumption * 7;
            weeklyForecast.add(Math.max(0, Math.round(remaining * 100.0) / 100.0));
        }

        String trend = predictedConsumption > currentConsumption ? "INCREASING"
                : predictedConsumption < currentConsumption ? "DECREASING" : "STABLE";

        return ForecastResponse.builder()
                .medicineId(ib.getMedicine().getMedicineId())
                .medicineName(ib.getMedicine().getName())
                .facilityId(ib.getFacility().getFacilityId())
                .facilityName(ib.getFacility().getName())
                .currentDailyConsumption(currentConsumption)
                .predictedDailyConsumption(predictedConsumption)
                .currentStock(ib.getClosingStock())
                .predictedDaysToStockout(predictedDays)
                .weeklyForecast(weeklyForecast)
                .trendDirection(trend)
                .build();
    }
}