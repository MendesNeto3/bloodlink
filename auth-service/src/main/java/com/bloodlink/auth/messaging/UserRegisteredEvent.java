package com.bloodlink.auth.messaging;

import java.util.UUID;

public record UserRegisteredEvent  (
        UUID userId,
        String email
){
}
