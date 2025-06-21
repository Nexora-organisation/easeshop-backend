package com.nexora.easeshop.controllers;

import com.nexora.easeshop.models.CartItem;
import com.nexora.easeshop.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // Ajouter un produit
    @PostMapping("/add/{productId}")
    public ResponseEntity<String> addToCart(@PathVariable Long productId) {
        cartService.addToCart(productId);
        return ResponseEntity.ok("Produit ajouté au panier");
    }

    // Afficher le contenu du panier
    @GetMapping
    public ResponseEntity<List<CartItem>> getCart() {
        return ResponseEntity.ok(cartService.getCustomerCartItems());
    }
}
