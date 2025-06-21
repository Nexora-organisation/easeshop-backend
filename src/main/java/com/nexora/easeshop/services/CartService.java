package com.nexora.easeshop.services;

import com.nexora.easeshop.models.Cart;
import com.nexora.easeshop.models.CartItem;
import com.nexora.easeshop.models.Product;
import com.nexora.easeshop.repositories.CartItemRepository;
import com.nexora.easeshop.repositories.CartRepository;
import com.nexora.easeshop.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CustomerService customerService;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final ProductService productService;

    public void addToCart(Long productId) {
        // 1. Vérifie si le produit existe
        Product product = productService.getProductById(productId);

        // 2. Récupère le panier de l'utilisateur
        Cart customerCart = this.getCustomerCart();

        // 3. Vérifie si le produit est déjà dans le panier
        Optional<CartItem> existingCartItem = customerCart.
                getCartItems().
                stream().
                filter(item -> item.getProduct().getId().equals(productId)).
                findFirst();

        if (existingCartItem.isPresent()) {
            throw new IllegalStateException("Le produit est déjà dans le panier");
        }

        // 4. Crée un nouvel item et l'ajoute au panier
        CartItem newItem = new CartItem();
        newItem.setQuantity(1);
        newItem.setProduct(product);
        newItem.setCart(customerCart);
        customerCart.getCartItems().add(newItem);

        // 5. Sauvegarder le panier en base de donnée
        cartRepository.save(customerCart);
    }

    // Récupérer le panier de l'utilisateur courant
    public Cart getCustomerCart() {
        return customerService.getCurrentUser().getCart();
    }

    //recuperer le contenu du panier de l'utilisateur courant
    public List<CartItem> getCustomerCartItems() {
        return getCustomerCart().getCartItems();
    }

}
