package org.dariusturcu.backend.model.auth;

public record AuthResponse(
        String token,
        String refreshToken,
        Long id,
        String username,
        String email
) {
}
