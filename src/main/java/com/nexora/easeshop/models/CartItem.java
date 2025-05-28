package com.nexora.easeshop.models;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class CartItem {
    private Long id;
    private Integer quantity;
}
