package com.nexora.easeshop.services;

import com.nexora.easeshop.dtos.customer.AuthenticationRequest;
import com.nexora.easeshop.dtos.customer.CreateCustomerDTO;
import com.nexora.easeshop.models.Cart;
import com.nexora.easeshop.models.Customer;
import com.nexora.easeshop.repositories.CustomerRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class CustomerService {
    @Value("${jwt.expiration}")
    private long EXPIRATION_TIME;

    @Value("${jwt.secret}")
    private String secret;

    private final BCryptPasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;

    public String login(AuthenticationRequest authenticationRequest) {
        Customer customer = customerRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow(EntityNotFoundException::new);
        if (passwordEncoder.matches(authenticationRequest.getPassword(), customer.getPassword())) {
            return this.generateToken(customer);
        }
        return null;
    }

    public String createCustomer(CreateCustomerDTO customer) {
        // Créer le panier
        Cart cart = new Cart();
        cart.setCartItems(new ArrayList<>());

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
        return this.generateToken(customertoSave);
    }

    public Customer getCurrentUser() {
        //obtenir l'email de l'utilisateur connecté
        String email = SecurityContextHolder.
                getContext().
                getAuthentication().
                getName();

        //obtenir l'utilisateur a partir de l'email
        Customer customer = customerRepository.findByEmail(email).
                orElseThrow(EntityNotFoundException::new);
        return customer;
    }

    public String generateToken(Customer customer) {
        return Jwts.builder()
                .setSubject(customer.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }
}
