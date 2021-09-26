package com.muscle.user.service;

import com.muscle.user.entity.PasswordToken;
import com.muscle.user.repository.PasswordTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordTokenService {

    private final PasswordTokenRepository passwordTokenRepository;

    public Optional<PasswordToken> getToken(String token) {
        return passwordTokenRepository.findByToken(token);
    }

    public void savePasswordToken(PasswordToken token) {
        passwordTokenRepository.save(token);
    }

    public void setConfirmedAt(String token) {
        PasswordToken passwordToken = passwordTokenRepository.findByToken(token).orElseThrow(() -> new IllegalStateException("Token not found"));
        passwordToken.setConfirmedAt(LocalDateTime.now());
        passwordTokenRepository.save(passwordToken);
    }
}
