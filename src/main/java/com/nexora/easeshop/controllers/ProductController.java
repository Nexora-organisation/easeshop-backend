package com.nexora.easeshop.controllers;

import com.nexora.easeshop.dtos.CreateProductDTO;
import com.nexora.easeshop.dtos.UpdateProductDTO;
import com.nexora.easeshop.models.Product;
import com.nexora.easeshop.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    //Créer un produit
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody CreateProductDTO dto) {
        Product createdProduct = productService.createProduct(dto);
        return ResponseEntity.ok(createdProduct);
    }

    //Mettre à jour un produit
    @PutMapping
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody UpdateProductDTO dto) {
        Product updatedProduct = productService.updateProduct(dto);
        return ResponseEntity.ok(updatedProduct);
    }

    //Supprimer un produit
    @DeleteMapping("/{productid}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productid) {
        productService.deleteProduct(productid);
        return ResponseEntity.noContent().build();
    }

    //Lister tous les produits
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    //Rechercher un produit par mot-clé
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        List<Product> results = productService.searchProducts(keyword);
        if (results == null || results.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(results);
    }

    //Obtenir un produit par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }
}
