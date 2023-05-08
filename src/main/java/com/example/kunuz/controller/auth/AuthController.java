package com.example.kunuz.controller.auth;

import com.example.kunuz.dto.auth.AuthDTO;
import com.example.kunuz.dto.auth.AuthResponseDTO;
import com.example.kunuz.dto.auth.RegistrationDTO;
import com.example.kunuz.dto.auth.RegistrationResponseDTO;
import com.example.kunuz.service.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/public")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
    @PostMapping("/public/register")
    public ResponseEntity<RegistrationResponseDTO> registration(@RequestBody @Valid RegistrationDTO dto) {
        return ResponseEntity.ok(authService.registration(dto));
    }
    @GetMapping("/public/email/verification/{jwt}")
    public ResponseEntity<RegistrationResponseDTO> emailVerification(@PathVariable("jwt") String jwt) {
        return ResponseEntity.ok(authService.emailVerification(jwt));
    }
}
