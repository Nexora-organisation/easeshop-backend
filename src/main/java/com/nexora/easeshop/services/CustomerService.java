package com.nexora.easeshop.services;

import com.nexora.easeshop.dtos.CreateCustomerDTO;
import com.nexora.easeshop.models.Cart;
import com.nexora.easeshop.models.Customer;
import com.nexora.easeshop.repositories.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final CustomerRepository customerRepository;

    public String createCustomer(CreateCustomerDTO customer) {
        // Créer le panier
        Cart cart = new Cart();
        cart.setCartItems(new ArrayList<>()); // pour éviter les NullPointerException

        // Créer le client
        Customer customertoSave = Customer.builder()
                .username(customer.getUsername())
                .email(customer.getEmail())
                .password(passwordEncoder.encode(customer.getPassword()))
                .cart(cart)
                .build();

        // Lier le client au panier
        cart.setCustomer(customertoSave);

        // Sauvegarder le client
        customerRepository.save(customertoSave);

        // Générer un token JWT
        return jwtService.generateToken(customertoSave);
    }

    public Customer getCurrentUser() {
        //obtenir l'email de l'utilisateur connecté
        String email = SecurityContextHolder.
                getContext().
                getAuthentication().
                getName();

        //obtenir l'utilisateur a partir de l'email
        Customer customer = customerRepository.findByUsername(email).
                orElseThrow(EntityNotFoundException::new);
        return customer;
    }
}
