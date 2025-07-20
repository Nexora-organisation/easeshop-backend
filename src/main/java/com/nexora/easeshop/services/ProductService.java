package com.nexora.easeshop.services;

import com.nexora.easeshop.dtos.CreateProductDTO;
import com.nexora.easeshop.dtos.UpdateProductDTO;
import com.nexora.easeshop.models.Product;
import com.nexora.easeshop.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Produit introuvable"));
    }
}
