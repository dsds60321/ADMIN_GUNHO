package dev.gunho.user.repository;

import dev.gunho.user.domain.Auth;
import dev.gunho.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Auth, Long> {
    boolean existsByUser(User user);

}
