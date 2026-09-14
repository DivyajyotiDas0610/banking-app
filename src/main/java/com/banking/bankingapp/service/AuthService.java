package com.banking.bankingapp.service;

import com.banking.bankingapp.exception.DuplicateResourceException;
import com.banking.bankingapp.model.Customer;
import com.banking.bankingapp.repository.CustomerRepository;
import com.banking.bankingapp.security.JwtUtil;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(
            CustomerRepository customerRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil) {

        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public Customer registerCustomer(String username, String password, String email) {

        if (customerRepository.existsByUsername(username)) {
            throw new DuplicateResourceException("Username already exists");
        }

        if (customerRepository.existsByEmail(email)) {
            throw new DuplicateResourceException("Email already exists");
        }

        Customer customer = new Customer();

        customer.setUsername(username);
        customer.setPassword(passwordEncoder.encode(password));
        customer.setEmail(email);
        customer.setCreatedAt(LocalDateTime.now());

        return customerRepository.save(customer);
    }

    public String login(String username, String password) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        return jwtUtil.generateToken(username);
    }
}