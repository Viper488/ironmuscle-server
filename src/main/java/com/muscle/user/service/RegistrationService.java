package com.muscle.user.service;

import com.muscle.email.service.EmailSender;
import com.muscle.trainings.service.PointService;
import com.muscle.user.dto.RegistrationRequestDto;
import com.muscle.user.entity.ConfirmationToken;
import com.muscle.user.entity.IronUser;
import com.muscle.user.entity.PasswordToken;
import com.muscle.user.entity.Role;
import com.muscle.user.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.muscle.email.service.impl.EmailService.*;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    @Value("${email.confirm.link}")
    public String confirmEmailBaseLink;
    @Value("${password.reset.link}")
    public String resetPasswordBaseLink;
    @Value("${user.create.link}")
    public String createUserBaseLink;
    private final RoleRepository roleRepository;
    private final UserService userService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final PasswordTokenService passwordTokenService;
    private final EmailSender emailSender;
    private final PointService pointService;

    public void register(RegistrationRequestDto request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if(!isValidEmail){
            throw new IllegalStateException("Email is not valid");
        }
        userService.validatePassword(request.getPassword());

        List<Role> roles = new ArrayList<>();
        for(String role:request.getRoles()) {
            roles.add(roleRepository.findByName(role));
        }

        String token = userService.signUpUser(
                IronUser.builder()
                        .username(request.getUsername())
                        .email(request.getEmail())
                        .password(request.getPassword())
                        .locked(false)
                        .enabled(false)
                        .roles(roles)
                        .build()

        );

        String link = confirmEmailBaseLink + token;
        emailSender.send(request.getEmail(), buildUserEmail(request.getUsername(), link));
    }

    @Transactional
    public void confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(() -> new IllegalStateException("Token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("Email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if(expiredAt != null && expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expired");
        }
        confirmationTokenService.setConfirmedAt(token);

        userService.enableUser(confirmationToken.getIronUser().getEmail());
        if(confirmationToken.getIronUser().getRoles().stream().filter(role -> role.getName().equals("USER")).collect(Collectors.toList()).size() > 0)
            pointService.initializePoints(confirmationToken.getIronUser());
    }

    public void initializeUser(RegistrationRequestDto request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if(!isValidEmail){
            throw new IllegalStateException("Email is not valid");
        }

        List<Role> roles = new ArrayList<>();
        for(String role:request.getRoles()) {
            roles.add(roleRepository.findByName(role));
        }

        IronUser user = IronUser.builder()
                            .username(request.getUsername())
                            .email(request.getEmail())
                            .locked(false)
                            .enabled(false)
                            .password("")
                            .roles(roles)
                            .build();

        String confirmToken = userService.signUpUser(user);

        String passwordToken = UUID.randomUUID().toString();

        passwordTokenService.savePasswordToken(
                        PasswordToken.builder()
                                .token(passwordToken)
                                .createdAt(LocalDateTime.now())
                                .expiresAt(LocalDateTime.now().plusHours(24))
                                .ironUser(user)
                                .build());

        String link = createUserBaseLink + confirmToken + "/" + passwordToken;
        emailSender.send(request.getEmail(), buildUserPasswordEmail(request.getUsername(), link));
    }
}
