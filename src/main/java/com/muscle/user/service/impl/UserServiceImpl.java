package com.muscle.user.service.impl;

import com.muscle.user.entity.ConfirmationToken;
import com.muscle.user.dto.IronUserDto;
import com.muscle.user.dto.RoleDto;
import com.muscle.user.entity.IronUser;
import com.muscle.user.entity.Role;
import com.muscle.user.repository.RoleRepository;
import com.muscle.user.repository.UserRepository;
import com.muscle.user.service.ConverterService;
import com.muscle.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final static String  USER_NOT_FOUND_MSG = "User %s not found in database";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ConverterService converterService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public IronUserDto saveUser(IronUserDto ironUserDto) {
        log.info("Saving new user {} to the database", ironUserDto.getUsername());
        return userRepository.save(converterService.convertDtoToUser(ironUserDto)).dto();
    }

    @Override
    public RoleDto saveRole(RoleDto roleDto) {
        log.info("Saving new role {} to the database", roleDto.getName());
        return roleRepository.save(converterService.convertDtoToRole(roleDto)).dto();
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        IronUser ironUser = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);

        ironUser.getRoles().add(role);
    }

    @Override
    public IronUserDto getUser(String username) {
        log.info("Fetching user {}", username);
        return userRepository.findByUsername(username).dto();
    }

    @Override
    public List<IronUserDto> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll()
                .stream()
                .map(IronUser::dto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    @Override
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
