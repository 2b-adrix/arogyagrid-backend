package com.arogyagrid.service;

import com.arogyagrid.dto.request.LoginRequest;
import com.arogyagrid.dto.request.RegisterRequest;
import com.arogyagrid.dto.response.AuthResponse;
import com.arogyagrid.entity.User;
import com.arogyagrid.exception.BadRequestException;
import com.arogyagrid.repository.UserRepository;
import com.arogyagrid.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final AuditLogService auditLogService;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .district(request.getDistrict())
                .facilityId(request.getFacilityId())
                .active(true)
                .build();

        user = userRepository.save(user);
        String token = tokenProvider.generateTokenFromEmail(user.getEmail());

        auditLogService.log("REGISTER", "USER", user.getId(),
                user.getEmail(), user.getRole().name(),
                "New user registered: " + user.getName());

        return buildAuthResponse(user, token);
    }

    public AuthResponse login(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()));

        String token = tokenProvider.generateToken(auth);
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        auditLogService.log("LOGIN", "USER", user.getId(),
                user.getEmail(), user.getRole().name(), "User logged in");

        return buildAuthResponse(user, token);
    }

    private AuthResponse buildAuthResponse(User user, String token) {
        return AuthResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .district(user.getDistrict())
                .facilityId(user.getFacilityId())
                .token(token)
                .tokenType("Bearer")
                .build();
    }
}