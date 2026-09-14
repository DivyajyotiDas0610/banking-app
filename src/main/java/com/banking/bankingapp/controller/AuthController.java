package com.banking.bankingapp.controller;

import com.banking.bankingapp.dto.LoginRequest;
import com.banking.bankingapp.dto.LoginResponse;
import com.banking.bankingapp.dto.RegisterRequest;
import com.banking.bankingapp.service.AuthService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterRequest request) {

        authService.registerCustomer(
                request.getUsername(),
                request.getPassword(),
                request.getEmail()
        );

        return ResponseEntity.ok("Customer registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        String token = authService.login(
                request.getUsername(),
                request.getPassword()
        );

        return ResponseEntity.ok(new LoginResponse(token));
    }
}