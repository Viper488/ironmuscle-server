package com.muscle.user;

import com.muscle.user.dto.RegistrationRequestDto;
import com.muscle.user.service.RegistrationService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @ApiOperation("Tworzy nowe konto dla użytkownika")
    @PostMapping()
    ResponseEntity<?> register(@RequestBody RegistrationRequestDto request) {
        try {
            registrationService.register(request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(generateErrorBody(400, e));
        }
    }

    /**
     * Confirm registration for users
     * @param token
     * @return
     */
    @ApiOperation("Potwierdza email użytkownika")
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
    @ApiOperation("Tworzy konto użytkonwika, trenera lub administratora")
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
