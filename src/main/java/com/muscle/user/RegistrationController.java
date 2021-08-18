package com.muscle.user;

import com.muscle.user.dto.RegistrationRequestDto;
import com.muscle.user.service.impl.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    @CrossOrigin
    @PostMapping()
    ResponseEntity<String> register(@RequestBody RegistrationRequestDto request){
        try {
            return ResponseEntity.ok().body(registrationService.register(request));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @CrossOrigin
    @GetMapping("/confirm")
    ResponseEntity<String> confirm(@RequestParam("token") String token){
        try {
            return ResponseEntity.ok().body(registrationService.confirmToken(token));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
