package dev.gunho.toooy.user.repository;

import dev.gunho.toooy.user.domain.Auth;
import dev.gunho.toooy.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Auth, Long> {
    boolean existsByUser(User user);

}
