package com.arogyagrid.controller;

import com.arogyagrid.dto.response.ApiResponse;
import com.arogyagrid.dto.response.BedsStaffResponse;
import com.arogyagrid.service.BedsStaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/beds-staff")
@RequiredArgsConstructor
public class BedsStaffController {

    private final BedsStaffService bedsStaffService;

    @GetMapping
    public ResponseEntity<ApiResponse<BedsStaffResponse>> get(
            @RequestParam(required = false) String district) {
        return ResponseEntity.ok(
                ApiResponse.ok(bedsStaffService.getBedsAndStaff(district)));
    }
}