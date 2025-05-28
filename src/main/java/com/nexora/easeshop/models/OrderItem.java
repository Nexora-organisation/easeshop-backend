package com.nexora.easeshop.models;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class OrderItem {
    private Long id;
    private Integer quantity;
}
