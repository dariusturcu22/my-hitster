package org.dariusturcu.backend.repository;

import org.dariusturcu.backend.model.user.AuthProvider;
import org.dariusturcu.backend.model.user.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByUsernameOrEmail(String username, String email);

    Boolean existsUserByUsername(String username);

    Boolean existsUserByEmail(String email);

    Optional<User> findUserByAuthProviderAndAuthProviderId(AuthProvider authProvider, String authProviderId);
}
