package com.bloodlink.auth.dto.request;

public record LoginRequest (
        String email,
        String password
) {
}
