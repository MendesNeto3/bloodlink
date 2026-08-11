package com.bloodlink.auth.dto.request;

import jakarta.validation.constraints.NotNull;

public class RefreshRequest {
    @NotNull
    public String refreshToken;
}
