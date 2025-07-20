package com.nexora.easeshop.repositories;

import com.nexora.easeshop.models.Customer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByUsername(String name);

    boolean existsByEmail(String email);

    Optional<Customer> findByKeycloakId(String keycloakId);
}
