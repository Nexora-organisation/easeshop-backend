package com.nexora.easeshop.models;

import jakarta.persistence.Entity;
import lombok.Data;

import java.time.LocalDate;

@Entity(name = "orders")
@Data
public class Order {
    private Long id;
    private OrderStatus payment_status;
    private String shipping_address;
    private LocalDate order_date;
}
