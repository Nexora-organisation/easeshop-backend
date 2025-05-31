package com.nexora.easeshop.controllers;

import com.nexora.easeshop.dtos.AuthenticationResponse;
import com.nexora.easeshop.dtos.CreateCustomerDTO;
import com.nexora.easeshop.dtos.CreateCustomerResponseDTO;
import com.nexora.easeshop.dtos.AuthenticationRequest;
import com.nexora.easeshop.models.Customer;
import com.nexora.easeshop.services.CustomUserDetailsService;
import com.nexora.easeshop.services.CustomerService;
import com.nexora.easeshop.services.JWTService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JWTService jwtService;

    @PostMapping("/register")
    public ResponseEntity<CreateCustomerResponseDTO> register(@Valid @RequestBody CreateCustomerDTO user) {
        String jwt = customerService.createCustomer(user);
        return ResponseEntity.
                ok(CreateCustomerResponseDTO.
                        builder().
                        message("Customer registered successfully").
                        jwt(jwt).
                        build());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        Customer user = (Customer) userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        String jwt = jwtService.generateToken(user);
        return ResponseEntity.ok(AuthenticationResponse.
                builder().
                jwt(jwt).
                message("Login successful").
                build());
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

}
