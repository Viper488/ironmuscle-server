package com.muscle.registration;

import com.muscle.registration.dto.RegistrationRequest;
import com.muscle.registration.service.impl.RegistrationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/registration")
public class RegistrationController {

    private final RegistrationServiceImpl registrationService;

    @PostMapping()
    public String register(@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }

    @GetMapping("/confirm")
    public String register(@RequestParam("token") String token){
        return registrationService.confirmToken(token);
    }
}
