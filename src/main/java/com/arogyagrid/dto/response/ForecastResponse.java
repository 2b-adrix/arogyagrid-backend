package com.arogyagrid.dto.response;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForecastResponse {
    private String medicineId;
    private String medicineName;
    private String facilityId;
    private String facilityName;
    private double currentDailyConsumption;
    private double predictedDailyConsumption;
    private int currentStock;
    private int predictedDaysToStockout;
    private List<Double> weeklyForecast;
    private String trendDirection;
}