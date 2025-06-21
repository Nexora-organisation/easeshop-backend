package com.nexora.easeshop.controllers;

import com.nexora.easeshop.dtos.customer.AuthenticationRequest;
import com.nexora.easeshop.dtos.customer.CreateCustomerDTO;
import com.nexora.easeshop.models.Customer;
import com.nexora.easeshop.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final CustomerService customerService;

    @PostMapping("/register")
    public String register(@Validated @RequestBody CreateCustomerDTO createCustomerDTO) {
        return customerService.createCustomer(createCustomerDTO);
    }

    @PostMapping("/login")
    public String login(@Validated @RequestBody AuthenticationRequest authenticationRequest) {
        return customerService.login(authenticationRequest);
    }

    @GetMapping("/me")
    public Customer getCurrentUser() {
        return customerService.getCurrentUser();
    }
}
