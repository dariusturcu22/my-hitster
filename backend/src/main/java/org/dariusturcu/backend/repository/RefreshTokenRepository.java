package org.dariusturcu.backend.repository;

import org.dariusturcu.backend.model.RefreshToken;
import org.dariusturcu.backend.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    void deleteRefreshTokenByUser(User user);
}
