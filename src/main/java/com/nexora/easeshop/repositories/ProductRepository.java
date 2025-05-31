package com.nexora.easeshop.repositories;

import com.nexora.easeshop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<List<Product>> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);
}

