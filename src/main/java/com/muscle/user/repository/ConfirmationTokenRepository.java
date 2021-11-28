package com.muscle.user.repository;

import com.muscle.user.entity.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken,Long> {

    Optional<ConfirmationToken> findByToken(String token);
    List<ConfirmationToken> findByExpiresAtBeforeAndConfirmedAtIsNull(LocalDateTime expiresAt);
}
