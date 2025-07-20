package com.nexora.easeshop.dtos.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginCustomerRequestDto(
        @NotBlank(message = "L'e-mail est obligatoire")
        @Email(message = "L'e-mail doit être valide")
        String email,

        @NotBlank(message = "Le mot de passe est obligatoire")
        @Size(min = 6, max = 100, message = "Le mot de passe doit contenir au moins 6 caractères")
        String password) {

}
