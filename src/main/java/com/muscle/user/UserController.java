package com.muscle.user;

import com.muscle.user.dto.*;
import com.muscle.user.service.impl.UserService;
import com.muscle.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtTokenUtil;

    /**
     * Login to the system
     * @param authenticationRequest
     * @return
     * @throws Exception
     */
    @PostMapping("/authenticate")
    ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    };

    /**
     * Show info about currently logged user
     * @param header
     * @return
     */
    @GetMapping("/myself")
    public IronUserDto getMyself(@RequestHeader("Authorization") String header){
        return userService.getMyself(header);
    }

    /** Welcome message for logged user
     *
     * @param header
     * @return
     */
    @GetMapping("/welcome")
    public String getWelcome(@RequestHeader("Authorization") String header){
        return userService.getWelcomeMsg(header);
    }

    /**
     * Send reset password email
     * @param header
     */
    @PostMapping("/password/change")
    public void requestPasswordChange(@RequestHeader("Authorization") String header) {
        userService.requestPasswordChange(header);
    }

    /**
     * Change password
     * @param header
     * @param password
     */
    @PutMapping("/password/reset")
    public void requestPasswordChange(@RequestHeader("Authorization") String header, @RequestParam("password") String password) {
        userService.resetPassword(header, password);
    }

    /**
     * Show all users by role
     */
    @GetMapping("/users")
    public List<IronUserDto> getUsers(@RequestParam("role") String roleName) {
        return userService.getUsersByRole(roleName);
    }
}
