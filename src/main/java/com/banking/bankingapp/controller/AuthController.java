package com.banking.bankingapp.controller;

import com.banking.bankingapp.model.Customer;
import com.banking.bankingapp.service.AuthService;
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
    public ResponseEntity<Customer> register(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String email) {

        Customer customer = authService.registerCustomer(
                username,
                password,
                email
        );

        return ResponseEntity.ok(customer);
    }
}