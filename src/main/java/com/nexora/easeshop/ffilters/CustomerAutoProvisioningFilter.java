package com.nexora.easeshop.ffilters;

import com.nexora.easeshop.services.CustomerService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CustomerAutoProvisioningFilter extends OncePerRequestFilter {
    private final CustomerService customerService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();

            String keycloakId = jwt.getSubject();
            String username = jwt.getClaimAsString("name");
            String email = jwt.getClaimAsString("email");
            String picture = jwt.getClaimAsString("picture");

            //jwt.getClaims().forEach((k, v) -> System.out.println(k + ": " + v));
            customerService.createIfNotExist(keycloakId, username, email, picture);

        }

        filterChain.doFilter(request, response);
    }
}
