package com.arogyagrid.controller;

import com.arogyagrid.dto.response.ApiResponse;
import com.arogyagrid.dto.response.ForecastResponse;
import com.arogyagrid.service.ForecastService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/forecast")
@RequiredArgsConstructor
public class ForecastController {

    private final ForecastService forecastService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ForecastResponse>>> getForecast(
            @RequestParam(required = false) String facilityId) {
        return ResponseEntity.ok(
                ApiResponse.ok(forecastService.getForecast(facilityId)));
    }
}