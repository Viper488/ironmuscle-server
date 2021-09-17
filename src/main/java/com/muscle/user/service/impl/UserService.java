package com.muscle.user.service.impl;

import com.muscle.email.service.EmailSender;
import com.muscle.user.dto.IronUserDetails;
import com.muscle.user.dto.RoleDto;
import com.muscle.user.entity.ConfirmationToken;
import com.muscle.user.dto.IronUserDto;
import com.muscle.user.entity.IronUser;
import com.muscle.user.entity.Role;
import com.muscle.user.repository.RoleRepository;
import com.muscle.user.repository.UserRepository;
import com.muscle.user.service.ConverterService;
import com.muscle.user.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final static String  USER_NOT_FOUND_MSG = "User %s not found in database";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ConverterService converterService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    private final JwtUtil jwtUtil;

    public RoleDto saveRole(RoleDto roleDto) {
        log.info("Saving new role {} to the database", roleDto.getName());
        return roleRepository.save(converterService.convertDtoToRole(roleDto)).dto();
    }

    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        IronUser ironUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        Role role = roleRepository.findByName(roleName);

        ironUser.getRoles().add(role);
    }

    public IronUserDto getMyself(String header) {
        String username = jwtUtil.extractUsernameFromHeader(header);

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found")).dto();
    }

    public String getWelcomeMsg(String header) {
        String username = jwtUtil.extractUsernameFromHeader(header);

        return "Welcome " + userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found")).dto().getUsername();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<IronUser> user = userRepository.findByUsername(username);

        if(!user.isPresent()) {
            user = userRepository.findByEmail(username);
        }

        return user.map(IronUser::dto).map(IronUserDetails::new).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
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
                .expiresAt(LocalDateTime.now().plusMinutes(15))
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

    public void requestPasswordChange(String header) {
        String username = jwtUtil.extractUsernameFromHeader(header);
        IronUserDto user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found")).dto();

        String link = "http://localhost:4200/reset/password";
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
                "<p>We have received your request to reset your <span class=\"il\">password</span>. Please click the link below to complete the reset:</p>\n"+
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

    public void resetPassword(String header, String password) {
        String username = jwtUtil.extractUsernameFromHeader(header);
        IronUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }
}
