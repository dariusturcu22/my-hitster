package org.dariusturcu.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dariusturcu.backend.model.auth.AuthResponse;
import org.dariusturcu.backend.model.auth.AuthResult;
import org.dariusturcu.backend.model.auth.LoginRequest;
import org.dariusturcu.backend.model.auth.RegisterRequest;
import org.dariusturcu.backend.security.util.JwtUtil;
import org.dariusturcu.backend.service.AuthService;
import org.dariusturcu.backend.util.CookieUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Authentication Management")

public class AuthController {
    private final AuthService authService;
    private final CookieUtil cookieUtil;
    private final JwtUtil jwtUtil;

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request,
            HttpServletResponse response
    ) {
        AuthResult result = authService.register(request);
        setTokenCookies(response, result);
        return ResponseEntity.ok(new AuthResponse(
                result.id(),
                result.username(),
                result.email()
        ));
    }

    @Operation(summary = "Login with username/email and password")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {
        AuthResult result = authService.login(request);
        setTokenCookies(response, result);
        return ResponseEntity.ok(new AuthResponse(
                result.id(),
                result.username(),
                result.email()
        ));
    }

    @Operation(summary = "Refresh access token using refresh token cookie")
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String refreshToken = cookieUtil.extractFromCookies(request, "refresh_token")
                .orElseThrow(() -> new RuntimeException("No refresh token present"));

        AuthResult result = authService.refreshTokens(refreshToken);
        setTokenCookies(response, result);
        return ResponseEntity.ok(new AuthResponse(
                result.id(),
                result.username(),
                result.email()
        ));
    }

    @Operation(summary = "Logout and clear auth tokens")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        cookieUtil.extractFromCookies(request, "refresh_token")
                .ifPresent(authService::revokeRefreshToken);

        response.addHeader(
                HttpHeaders.SET_COOKIE,
                cookieUtil.deleteCookie("access_token", "/")
                        .toString()
        );
        response.addHeader(
                HttpHeaders.SET_COOKIE,
                cookieUtil.deleteCookie("refresh_token", "/auth/refresh")
                        .toString()
        );

        return ResponseEntity.ok().build();
    }

    private void setTokenCookies(HttpServletResponse response, AuthResult result) {
        response.addHeader(HttpHeaders.SET_COOKIE,
                cookieUtil.createAccessTokenCookie(
                        result.accessToken(),
                        jwtUtil.getExpirationSeconds()
                ).toString());
        response.addHeader(HttpHeaders.SET_COOKIE,
                cookieUtil.createAccessTokenCookie(
                        result.refreshToken(),
                        jwtUtil.getRefreshExpirationSeconds()
                ).toString());
    }
}
