package com.nexora.easeshop.models;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Product {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String image_product_url;
}
