package com.bloodlink.auth.dto.response;

public record TokenResponse (
        String accessToken,
        String refreshToken
) {
}
