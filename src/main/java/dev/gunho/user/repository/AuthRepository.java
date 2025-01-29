package dev.gunho.user.repository;

import dev.gunho.user.entity.Auth;
import dev.gunho.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Auth, Long> {
    boolean existsByUser(User user);

}
