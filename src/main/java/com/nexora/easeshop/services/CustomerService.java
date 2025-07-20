package com.nexora.easeshop.services;


import com.nexora.easeshop.models.Cart;
import com.nexora.easeshop.models.Customer;
import com.nexora.easeshop.repositories.CustomerRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Customer createIfNotExist(String keycloakId, String username, String email, String pictureUrl) {
        return customerRepository.findByEmail(email)
                .orElseGet(() -> {
                    Cart cart = new Cart();

                    Customer newCustomer = Customer.builder()
                            .keycloakId(keycloakId)
                            .username(username)
                            .email(email)
                            .profile_picture_url(pictureUrl)
                            .cart(cart)
                            .build();

                    // lie le client au panier
                    cart.setCustomer(newCustomer);

                    return customerRepository.save(newCustomer);
                });
    }


    public Customer getCurrentUser() {
        //obtenir l'email de l'utilisateur connecté
        Jwt jwt = (Jwt) SecurityContextHolder.
                getContext().
                getAuthentication().
                getPrincipal();
        String email = jwt.getClaimAsString("email");
        //obtenir l'utilisateur a partir de l'email
        return customerRepository.findByEmail(email).
                orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable"));
    }
}
