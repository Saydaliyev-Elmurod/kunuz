package com.example.kunuz.controller;

import com.example.kunuz.dto.AuthDTO;
import com.example.kunuz.dto.AuthResponseDTO;
import com.example.kunuz.dto.RegistrationDTO;
import com.example.kunuz.dto.RegistrationResponseDTO;
import com.example.kunuz.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
    @PostMapping("/register")
    public ResponseEntity<RegistrationResponseDTO> registration(@RequestBody RegistrationDTO dto) {
        return ResponseEntity.ok(authService.registration(dto));
    }
    @GetMapping("/email/verification/{jwt}")
    public ResponseEntity<RegistrationResponseDTO> emailVerification(@PathVariable("jwt") String jwt) {
        return ResponseEntity.ok(authService.emailVerification(jwt));
    }
}
