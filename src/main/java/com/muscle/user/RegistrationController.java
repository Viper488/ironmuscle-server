package com.muscle.user;

import com.muscle.user.dto.RegistrationRequestDto;
import com.muscle.user.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    ResponseEntity<String> register(@RequestBody RegistrationRequestDto request){
        try {
            return ResponseEntity.ok().body(registrationService.register(request));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Confirm registration for users
     * @param token
     * @return
     */
    @GetMapping("/confirm")
    ResponseEntity<String> confirm(@RequestParam("token") String token){
        try {
            return ResponseEntity.ok().body(registrationService.confirmToken(token));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
