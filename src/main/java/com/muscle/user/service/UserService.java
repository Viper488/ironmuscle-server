package com.muscle.user.service;

import com.muscle.email.service.EmailSender;
import com.muscle.user.dto.*;
import com.muscle.user.entity.ConfirmationToken;
import com.muscle.user.entity.IronUser;
import com.muscle.user.entity.PasswordToken;
import com.muscle.user.repository.UserRepository;
import com.muscle.user.response.IronUserResponse;
import com.muscle.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    @Value("${password.reset.link}")
    private String resetPasswordBaseLink;
    private final static String  USER_NOT_FOUND_MSG = "User %s not found in database";
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final PasswordTokenService passwordTokenService;
    private final EmailSender emailSender;
    private final JwtUtil jwtUtil;

    public IronUser getUserFromHeader(String header) {
        return userRepository.findByUsername(jwtUtil.extractUsername(header))
                .orElseThrow(() -> new IllegalStateException("User not found"));
    }

    public IronUserResponse getMyself(String header) {
        return getUserFromHeader(header).response();
    }

    public String getWelcomeMsg(String header) {
        return "Welcome " + getUserFromHeader(header).getUsername();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<IronUser> user = userRepository.findByUsername(username);


        if(!user.isPresent()) {
            user = userRepository.findByEmail(username);
        }
        IronUserDto ironUserDto = user.orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username))).dto();
        return IronUserDetails.builder()
                .id(ironUserDto.getId())
                .name(ironUserDto.getName())
                .lastName(ironUserDto.getLastName())
                .username(ironUserDto.getUsername())
                .email(ironUserDto.getEmail())
                .password(ironUserDto.getPassword())
                .locked(ironUserDto.getLocked())
                .enabled(ironUserDto.getEnabled())
                .authorities(ironUserDto.getRoles().stream().map(roleDto -> new SimpleGrantedAuthority(roleDto.getName())).collect(Collectors.toList()))
                .build();
    }

    public String signUpUser(IronUser ironUser){
        boolean userExists = userRepository
                .findByEmail(ironUser.getEmail())
                .isPresent();

        if(userExists) {
            throw new IllegalStateException("User already exists");
        }

        String encodedPassword = passwordEncoder.encode(ironUser.getPassword());

        ironUser.setPassword(encodedPassword);

        userRepository.save(ironUser);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = ConfirmationToken.builder()
                .token(token)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusHours(24))
                .ironUser(ironUser)
                .build();

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public void enableUser(String email) {
        IronUser ironUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        ironUser.setEnabled(true);

        userRepository.save(ironUser);
    }

    public List<IronUserDto> getUsersByRole(String roleName) {
        return userRepository.findByRole(roleName).stream().map(IronUser::dto).collect(Collectors.toList());
    }

    public Page<IronUser> getPaginatedUsers(Pageable pageable, String query) {
        return userRepository.findByUsernameContainsOrderByUsernameAsc(pageable, query);
    }

    public void requestPasswordChange(String email) {
        IronUser user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalStateException("User with this email doesn't exist. Try different email"));


        String token = UUID.randomUUID().toString();

        PasswordToken passwordToken = PasswordToken.builder()
                .token(token)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusHours(24))
                .ironUser(user)
                .build();

        passwordTokenService.savePasswordToken(passwordToken);

        String link = resetPasswordBaseLink + token;
        emailSender.send(user.getEmail(), buildPasswordEmail(user.getUsername(), link));
    }

    private String buildPasswordEmail(String name, String link) {
        return "<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\"  style=\"border-collapse:collapse;width:100%;min-width:100%;height:auto\">\n"+
                "<tbody>\n"+
                "<tr>\n"+
                "<td width=\"100%\" valign=\"top\" bgcolor=\"#ffffff\" style=\"padding-top:20px\">\n"+
                "<table width=\"580\" class=\"m_-5530667538910658334deviceWidth\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" bgcolor=\"#ffffff\" style=\"border-collapse:collapse;margin:0 auto\">\n"+
                "<tbody>\n"+
                "<tr>\n"+
                "<td style=\"font-size:13px;color:#282828;font-weight:normal;text-align:left;font-family:'Open Sans',sans-serif;line-height:24px;vertical-align:top;padding:15px 8px 10px 8px\" bgcolor=\"#ffffff\">\n"+
                "<h1 style=\"text-align:center;font-weight:600;margin:30px 0 50px 0\"><span class=\"il\">PASSWORD</span> RESET REQUEST</h1>\n"+
                "<p>Dear "+ name +",</p>\n"+
                "<p>We have received your request to reset your <span class=\"il\">password</span>. Please click the link below to complete the reset. <b>This password reset is only valid for the next 24 hours.</b></p>\n"+
                "</td>\n"+
                "</tr>\n"+
                "<tr>\n"+
                "<td style=\"padding-bottom:30px\">\n"+
                "<a href=\"" + link +"\" style=\"padding:10px;width:300px;display:block;text-decoration:none;border:1px solid #ff6c37;text-align:center;font-weight:bold;font-size:14px;font-family:'Open Sans',sans-serif;color:#ffffff;background:#ff6c37;border-radius:5px; line-height:17px;margin:0 auto\" target=\"_blank\">\n"+
                "Reset My <span class=\"il\">Password</span>\n"+
                "</a>\n"+
                "</td>\n"+
                "</tr>\n"+
                "<tr>\n"+
                "<td style=\"font-family:'Open Sans',sans-serif;font-size:13px;padding:0px 10px 0px 10px;text-align:left\">\n"+
                "<p>If you need additional assistance, or you did not make this <span class=\"il\">change</span>, please contact \n"+
                "<a href=\"mailto:help@ironmuscle.com\" style=\"color:#ff6c37;text-decoration:underline;font-weight:bold\" target=\"_blank\">help@ironmuscle.com</a>.</p>\n"+
                "<p>Cheers,<br>The IronMuscle Team</p>\n"+
                "</td>\n"+
                "</tr>\n"+
                "</tbody></table>\n"+
                "</td>\n"+
                "</tr>\n"+
                "</tbody>\n"+
                "</table>";
    }

    @Transactional
    public void resetPassword(ResetPasswordDto resetPasswordDto) {
        PasswordToken passwordToken = passwordTokenService.getToken(resetPasswordDto.getToken())
                .orElseThrow(() -> new IllegalStateException("Token not found"));

        IronUser user = userRepository.findByUsername(passwordToken.getIronUser().getUsername())
                .orElseThrow(() -> new IllegalStateException("User not found"));

        if (passwordToken.getConfirmedAt() != null) {
            throw new IllegalStateException("Link expired");
        }

        LocalDateTime expiredAt = passwordToken.getExpiresAt();

        if(expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Link expired");
        }

        validatePassword(resetPasswordDto.getPassword());

        String encodedPassword = passwordEncoder.encode(resetPasswordDto.getPassword());
        if(encodedPassword.equals(user.getPassword())) {
            throw new IllegalStateException("Your new password can not be the same as your old password");
        }
        user.setPassword(encodedPassword);
        passwordTokenService.setConfirmedAt(resetPasswordDto.getToken());

        userRepository.save(user);
    }

    public void changePassword(String header, ChangePasswordDto changePasswordDto) {
        IronUser user = getUserFromHeader(header);

        if(!passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())) {
            throw new IllegalStateException("Old password you provided is incorrect");
        }

        String newPassword = passwordEncoder.encode(changePasswordDto.getNewPassword());

        if(newPassword.equals(user.getPassword())) {
            throw new IllegalStateException("Your new password can not be the same as your old password");
        }

        validatePassword(changePasswordDto.getNewPassword());
        user.setPassword(newPassword);

        userRepository.save(user);
    }

    public void changeMyDetails(String header, ChangeUserDetailsDto changed) {
        IronUser user = getUserFromHeader(header);

        if(!userRepository.findByEmail(changed.getEmail()).isPresent()) {
            user.setEmail(changed.getEmail());
            userRepository.save(user);
        }
        else
            throw new IllegalStateException("Email already taken");
    }

    public void changeUserDetails(Long id, ChangeUserDetailsDto changed) {
        IronUser user = userRepository.findById(id)
                .orElseThrow(()-> new IllegalStateException("User not found"));

        if(user.getLocked() == changed.isLock()) {
            if(user.getLocked()){
                throw new IllegalStateException("User is already locked");
            } else {
                throw new IllegalStateException("User is already unlocked");
            }
        } else {
            user.setLocked(changed.isLock());
            userRepository.save(user);
        }
    }

    public void validatePassword(String password) {
        if(!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")){
            throw new IllegalStateException("Password must contain minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character");
        }
    }
}
