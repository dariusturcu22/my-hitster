package org.dariusturcu.backend.repository;

import org.dariusturcu.backend.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
