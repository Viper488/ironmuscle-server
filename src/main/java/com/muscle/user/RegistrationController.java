package com.muscle.user;

import com.muscle.user.dto.RegistrationRequestDto;
import com.muscle.user.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    void register(@RequestBody RegistrationRequestDto request) throws IOException {
        registrationService.register(request);
    }

    /**
     * Confirm registration for users
     * @param token
     * @return
     */
    @GetMapping("/confirm")
    void confirm(@RequestParam("token") String token){
        registrationService.confirmToken(token);
    }

    /**
     * Initialize user by admin
     * @param request
     * @return
     */

    @PostMapping("/user")
    ResponseEntity initializeUser(@RequestBody RegistrationRequestDto request){
        try {
            registrationService.initializeUser(request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(generateErrorBody(400, e));
        }
    }
}
