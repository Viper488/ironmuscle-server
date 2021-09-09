package com.muscle.user.service.impl;

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
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final static String  USER_NOT_FOUND_MSG = "User %s not found in database";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ConverterService converterService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
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
        String username = getUsernameFromHeader(header);

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found")).dto();
    }

    public String getWelcomeMsg(String header) {
        String username = getUsernameFromHeader(header);

        return "Welcome " + userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found")).dto().getUsername();
    }

    public String getUsernameFromHeader(String header) {
        if(header != null && header.startsWith("Bearer ")) {
            String jwt = header.substring(7);

            return jwtUtil.extractUsername(jwt);
        }
        return null;
    }

    public List<IronUserDto> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll()
                .stream()
                .map(IronUser::dto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<IronUser> user = userRepository.findByUsername(username);

        if(user.isEmpty()) {
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
}
