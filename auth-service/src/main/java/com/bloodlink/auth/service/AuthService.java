package com.bloodlink.auth.service;


import com.bloodlink.auth.dto.request.LoginRequest;
import com.bloodlink.auth.dto.request.RefreshRequest;
import com.bloodlink.auth.dto.request.RegisterRequest;
import com.bloodlink.auth.dto.response.TokenResponse;
import com.bloodlink.auth.dto.response.UserResponse;

import java.util.UUID;

public interface AuthService {
    UserResponse register(RegisterRequest request);

    TokenResponse login(LoginRequest request);

    TokenResponse refresh(RefreshRequest request);

    UserResponse findById(UUID id);
}