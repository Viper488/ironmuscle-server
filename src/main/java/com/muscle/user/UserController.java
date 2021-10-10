package com.muscle.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.muscle.user.dto.*;
import com.muscle.user.response.IronUserResponse;
import com.muscle.user.service.UserService;
import com.muscle.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final JwtUtil jwtUtil;
    private final UserService userService;
/*    *//**//**
     * Login for users
     * @param authenticationRequest
     * @return
     *//*
*//*
    @CrossOrigin
    @PostMapping("/authenticate")
    ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect username or password", e);
        }
        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());
        boolean higherAuthority = false;
        for(GrantedAuthority authority : userDetails.getAuthorities()) {
            if (authority.getAuthority().equals("EMPLOYEE") || authority.getAuthority().equals("ADMIN"))
                higherAuthority = true;
        }
        if(higherAuthority)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect username or password");

        return ResponseEntity.ok(jwtTokenUtil.generateTokens(userDetails));
    }

    *//*
    *//**
     * Login for employees
     * @return
     *//*
    *//*
    @PostMapping("/system/authenticate")
    ResponseEntity<?> createAuthenticationTokenEmployee(@RequestBody AuthenticationRequest authenticationRequest) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect username or password", e);
        }
        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());

        boolean lowerAuthority = false;
        for(GrantedAuthority authority : userDetails.getAuthorities()) {
            if (authority.getAuthority().equals("USER"))
                lowerAuthority = true;
        }
        if (lowerAuthority)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect username or password");

        return ResponseEntity.ok(jwtTokenUtil.generateTokens(userDetails));
    }*/

    @GetMapping("/token/refresh")
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String authorizationHeader = request.getHeader(AUTHORIZATION);
            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    String refresh_token = authorizationHeader.substring("Bearer ".length());
                    String username = jwtUtil.extractUsername(authorizationHeader);

                    UserDetails userDetails = userService.loadUserByUsername(username);
                    String access_token = jwtUtil.createToken(request, userDetails, 1000 * 60);

                    Map<String, String> tokens = new HashMap<>();
                    tokens.put("access_token", access_token);
                    tokens.put("refresh_token", refresh_token);
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), tokens);

                } catch (Exception e) {

                    response.setStatus(UNAUTHORIZED.value());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), jwtUtil.generateErrorResponse(UNAUTHORIZED, request.getServletPath(), e.getMessage()));
                }
            } else {
                throw new RuntimeException("Refresh token is missing");
            }
    }

    /** Welcome message for logged user
     *
     * @param header
     * @return
     */
    @GetMapping("/welcome")
    public String getWelcome(@RequestHeader("Authorization") String header) throws Exception {
        return userService.getWelcomeMsg(header);
    }

    /**
     * Show info about currently logged user
     * @param header
     * @return
     */
    @GetMapping("/myself")
    public IronUserResponse getMyself(@RequestHeader("Authorization") String header) throws Exception {
        return userService.getMyself(header);
    }

    /**
     * Change user details
     * @param header
     * @param changeUserDetailsDto
     */
    @PutMapping("/myself")
    public void changeUserDetails(@RequestHeader("Authorization") String header, @RequestBody ChangeUserDetailsDto changeUserDetailsDto) throws Exception {
        userService.changeUserDetails(header, changeUserDetailsDto);
    }

    /**
     * Send reset password email
     * @param email
     */
    @PostMapping("/password/reset")
    public void requestPasswordReset(@RequestParam("email") String email) {
        userService.requestPasswordChange(email);
    }
    /**
     * Change password
     * @param resetPasswordDto
     */
    @PutMapping("/password/reset")
    public void resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        userService.resetPassword(resetPasswordDto);
    }

    /**
     * Change password
     * @param header
     * @param changePasswordDto
     */
    @PostMapping("/password/change")
    public void changePassword(@RequestHeader("Authorization") String header, @RequestBody ChangePasswordDto changePasswordDto) throws Exception {
        userService.changePassword(header, changePasswordDto);
    }

    /**
     * Show all users by role
     */
    @GetMapping("/users")
    public List<IronUserDto> getUsers(@RequestParam("role") String roleName) {
        return userService.getUsersByRole(roleName);
    }

    //TODO: Bezpieczne przesylanie nowego hasła - od strony klienta
    //TODO: Role dla pracowników(trener itd) - done
    //TODO: Template treningi dla wszystkich - done
    //TODO: ???
}
