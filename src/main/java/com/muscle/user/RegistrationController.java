package com.muscle.user;

import com.muscle.user.dto.RegistrationRequestDto;
import com.muscle.user.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.muscle.user.util.JwtUtil.generateErrorBody;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    /**
     * Register for users
     * @param request
     * @return
     */

    @PostMapping()
    ResponseEntity<?> register(@RequestBody RegistrationRequestDto request) {
        registrationService.register(request);
        return ResponseEntity.ok().build();
    }

    /**
     * Confirm registration for users
     * @param token
     * @return
     */
    @GetMapping("/confirm")
    ResponseEntity<?> confirm(@RequestParam("token") String token){
        try {
            registrationService.confirmToken(token);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(generateErrorBody(401, e));
        }
    }

    /**
     * Initialize user by admin
     * @param request
     * @return
     */

    @PostMapping("/user")
    ResponseEntity<?> initializeUser(@RequestBody RegistrationRequestDto request){
        try {
            registrationService.initializeUser(request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(generateErrorBody(400, e));
        }
    }
}
