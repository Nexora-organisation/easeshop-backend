package com.nexora.easeshop.dtos.customer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCustomerResponseDTO {
    private String message;
    private String jwt;
}
