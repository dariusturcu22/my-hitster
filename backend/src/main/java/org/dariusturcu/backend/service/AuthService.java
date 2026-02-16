package org.dariusturcu.backend.service;

import lombok.RequiredArgsConstructor;
import org.dariusturcu.backend.model.auth.AuthResponse;
import org.dariusturcu.backend.model.auth.LoginRequest;
import org.dariusturcu.backend.model.auth.RegisterRequest;
import org.dariusturcu.backend.model.user.AuthProvider;
import org.dariusturcu.backend.model.user.Role;
import org.dariusturcu.backend.model.user.User;
import org.dariusturcu.backend.repository.UserRepository;
import org.dariusturcu.backend.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsUserByUsername(request.username())) {
            throw new RuntimeException("Username already exists");
            // TODO change to custom exception
        }

        if (userRepository.existsUserByEmail(request.email())) {
            throw new RuntimeException("Email already exists");
            // TODO change to custom exception
        }

        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setAuthProvider(AuthProvider.LOCAL);
        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);

        String token = jwtUtil.generateToken(savedUser);

        return new AuthResponse(
                token,
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail()
        );
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.usernameOrEmail(),
                        request.password()
                )
        );

        User user = userRepository.findUserByUsernameOrEmail(request.usernameOrEmail(), request.usernameOrEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        // TODO change to custom exception

        String token = jwtUtil.generateToken(user);

        return new AuthResponse(
                token,
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
