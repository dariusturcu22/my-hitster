package org.dariusturcu.backend.model.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Username and email are required")
        String email,

        @NotBlank(message = "Password is required")
        String password
) {
}
