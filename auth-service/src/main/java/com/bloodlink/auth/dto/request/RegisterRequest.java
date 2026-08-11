package com.bloodlink.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

public record RegisterRequest(
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        String name,
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        String email,
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        String password

) {
}
