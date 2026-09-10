package com.banking.bankingapp.service;

import com.banking.bankingapp.model.Customer;
import com.banking.bankingapp.repository.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(CustomerRepository customerRepository,
                       PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Customer registerCustomer(String username, String password, String email) {

        Customer customer = new Customer();

        customer.setUsername(username);
        customer.setPassword(passwordEncoder.encode(password));
        customer.setEmail(email);
        customer.setCreatedAt(LocalDateTime.now());

        return customerRepository.save(customer);
    }
}