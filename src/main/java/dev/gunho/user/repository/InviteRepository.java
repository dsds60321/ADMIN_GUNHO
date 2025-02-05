package dev.gunho.user.repository;

import dev.gunho.user.entity.Invite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InviteRepository extends JpaRepository<Invite, Long> {

    Invite findByInviterIdxAndEmail(Long inviterIdx, String email);

    Optional<Invite> findByToken(String token);
}
