package com.bloodlink.auth.controller;

import com.bloodlink.auth.dto.request.LoginRequest;
import com.bloodlink.auth.dto.request.RefreshRequest;
import com.bloodlink.auth.dto.request.RegisterRequest;
import com.bloodlink.auth.dto.response.TokenResponse;
import com.bloodlink.auth.dto.response.UserResponse;
import com.bloodlink.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        UserResponse userResponse = authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        TokenResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@Valid @RequestBody RefreshRequest refreshRequest) {
        TokenResponse response = authService.refresh(refreshRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/find")
    public ResponseEntity<UserResponse> findById (@AuthenticationPrincipal UUID id) {
        UserResponse response = authService.findById(id);
        return ResponseEntity.ok(response);
    }
}
