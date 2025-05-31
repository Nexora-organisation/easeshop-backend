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

    public Product createProduct(CreateProductDTO createProductDto) {
        Product productToStore = Product.builder()
                .name(createProductDto.getName())
                .description(createProductDto.getDescription())
                .price(createProductDto.getPrice())
                .imageProductUrl(createProductDto.getImageProductUrl())
                .build();
        return productRepository.save(productToStore);
    }

    public void deleteProduct(Long productId) {
        // Vérifie si le produit existe
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Produit introuvable avec l'id " + productId));

        // Supprime le produit
        productRepository.delete(product);
    }

    public Product updateProduct(UpdateProductDTO updateProductDTO) {
        Product existingProduct = productRepository.findById(updateProductDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Produit non trouvé avec l'id " + updateProductDTO.getId()));

        // Mettre à jour les champs
        existingProduct.setName(updateProductDTO.getName());
        existingProduct.setDescription(updateProductDTO.getDescription());
        existingProduct.setPrice(updateProductDTO.getPrice());
        existingProduct.setImageProductUrl(updateProductDTO.getImageProductUrl());

        // Enregistrer et retourner
        return productRepository.save(existingProduct);
    }


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> searchProducts(String keyword) {
        return productRepository.
                findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword).
                orElse(null);
    }
}
