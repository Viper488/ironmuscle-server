package com.muscle.user.repository;

import com.muscle.user.entity.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordTokenRepository extends JpaRepository<PasswordToken,Long> {
    Optional<PasswordToken> findByToken(String token);
    Optional<PasswordToken> findByIronUserUsername(String username);
}
