package com.bloodlink.auth.dto.response;

import com.bloodlink.auth.domain.model.Enums.UserRole;

import java.util.UUID;

public record UserResponse (
        UUID id,

        String name,

        String email,

        UserRole role
) {
}
