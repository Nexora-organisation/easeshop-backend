package com.nexora.easeshop.controllers;

import com.nexora.easeshop.dtos.customer.AuthenticationResponseDto;
import com.nexora.easeshop.dtos.customer.LoginCustomerRequestDto;
import com.nexora.easeshop.dtos.customer.RegisterCustomerRequestDto;
import com.nexora.easeshop.models.Customer;
import com.nexora.easeshop.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/me")
    public UserInfoDto getCurrentUser(JwtAuthenticationToken authenticationToken) {
        String username = authenticationToken.getToken().getClaimAsString(StandardClaimNames.PREFERRED_USERNAME);
        List<String> roles = authenticationToken.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return new UserInfoDto(username, roles);
    }

    public static record UserInfoDto(String name, List<String> roles) {
    }

}
