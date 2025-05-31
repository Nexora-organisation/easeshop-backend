package com.nexora.easeshop.services;

import com.nexora.easeshop.dtos.CreateCustomerDTO;
import com.nexora.easeshop.models.Customer;
import com.nexora.easeshop.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final CustomerRepository customerRepository;

    public String createCustomer(CreateCustomerDTO customer) {
        Customer customertoSave = Customer.
                builder().
                username(customer.getUsername()).
                email(customer.getEmail()).
                password(passwordEncoder.encode(customer.getPassword())).
                build();
        customerRepository.save(customertoSave);
        return jwtService.generateToken(customertoSave);
    }
}
