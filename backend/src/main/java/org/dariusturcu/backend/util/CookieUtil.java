package org.dariusturcu.backend.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
public class CookieUtil {
    public ResponseCookie createCookie(
            String token,
            long maxAgeSeconds,
            String name,
            String path
    ) {
        return ResponseCookie.from(name, token)
                .httpOnly(true)
                // .secure(true) // production
                .secure(false) // development
                .sameSite("Strict")
                .path(path)
                .maxAge(maxAgeSeconds)
                .build();
    }

    public ResponseCookie createAccessTokenCookie(String token, long maxAgeSeconds) {
        return createCookie(token, maxAgeSeconds, "access_token", "/");
    }

    public ResponseCookie createRefreshTokenCookie(String token, long maxAgeSeconds) {
        return createCookie(token, maxAgeSeconds, "refresh_token", "/auth/refresh");
    }

    public ResponseCookie deleteCookie(String name, String path) {
        return createCookie("", 0, name, path);
    }

    public Optional<String> extractFromCookies(HttpServletRequest request, String name) {
        if (request.getCookies() == null) return Optional.empty();
        return Arrays.stream(request.getCookies())
                .filter(cookie -> name.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst();
    }
}
