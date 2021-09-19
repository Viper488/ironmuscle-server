package com.muscle.user;

import com.muscle.user.dto.*;
import com.muscle.user.service.UserService;
import com.muscle.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
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
     * Login for users
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
        boolean higherAuthority = false;
        for(GrantedAuthority authority : userDetails.getAuthorities()) {
            if (authority.getAuthority().equals("EMPLOYEE") || authority.getAuthority().equals("ADMIN"))
                higherAuthority = true;
        }
        if(higherAuthority)
            throw new Exception("Incorrect username or password");

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    /**
     * Login for employees
     * @param authenticationRequest
     * @return
     * @throws Exception
     */
    @PostMapping("/system/authenticate")
    ResponseEntity<?> createAuthenticationTokenEmployee(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());

        boolean lowerAuthority = false;
        for(GrantedAuthority authority : userDetails.getAuthorities()) {
            if (authority.getAuthority().equals("USER"))
                lowerAuthority = true;
        }
        if (lowerAuthority)
            throw new Exception("Incorrect username or password");

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
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
     * Show info about currently logged user
     * @param header
     * @return
     */
    @GetMapping("/myself")
    public IronUserDto getMyself(@RequestHeader("Authorization") String header){
        return userService.getMyself(header);
    }

    /**
     * Change user details
     * @param header
     * @param changeUserDetailsDto
     */
    @PutMapping("/myself")
    public void changePassword(@RequestHeader("Authorization") String header, @RequestBody ChangeUserDetailsDto changeUserDetailsDto) {
        userService.changeUserDetails(header, changeUserDetailsDto);
    }

    /**
     * Send reset password email
     * @param header
     */
    @PostMapping("/password/reset")
    public void requestPasswordReset(@RequestHeader("Authorization") String header) {
        userService.requestPasswordChange(header);
    }

    //TODO: Bezpieczne przesylanie nowego hasła - od strony klienta
    //TODO: Role dla pracowników(trener itd) - done
    //TODO: Request o nowy trening
    //TODO: Wysylanie requestow do trenera z najmniejszym oblozeniem??? innym kryterium???
    //TODO: Template treningi dla wszystkich
    //TODO: ???

    /**
     * Change password
     * @param header
     * @param password
     */
    @PutMapping("/password/reset")
    public void resetPassword(@RequestHeader("Authorization") String header, @RequestParam("password") String password) {
        userService.resetPassword(header, password);
    }

    /**
     * Change password
     * @param header
     * @param changePasswordDto
     */
    @PostMapping("/password/change")
    public void changePassword(@RequestHeader("Authorization") String header, @RequestBody ChangePasswordDto changePasswordDto) {
        userService.changePassword(header, changePasswordDto);
    }

    /**
     * Show all users by role
     */
    @GetMapping("/users")
    public List<IronUserDto> getUsers(@RequestParam("role") String roleName) {
        return userService.getUsersByRole(roleName);
    }
}
