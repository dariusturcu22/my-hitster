package org.dariusturcu.backend.model.auth;

public record AuthResponse(
        String token,
        String type,
        Long id,
        String username,
        String email
) {
    public AuthResponse(String token, Long id, String username, String email) {
        this(token, "Bearer", id, username, email);
    }
}
