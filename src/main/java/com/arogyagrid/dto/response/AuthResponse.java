package com.arogyagrid.dto.response;

import com.arogyagrid.enums.UserRole;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String id;
    private String name;
    private String email;
    private UserRole role;
    private String district;
    private String facilityId;
    private String token;
    private String tokenType = "Bearer";
}