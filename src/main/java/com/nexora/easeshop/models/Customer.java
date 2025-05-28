package com.nexora.easeshop.models;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Customer {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String profile_picture_url;
}
