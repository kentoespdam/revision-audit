package com.kentoes.revisionaudit.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private static final String BEARER = "Bearer ";
    private static final String AUTHORIZATION = "Authorization";
    @Value("${spring.profiles.active}")
    String profile;

    @SuppressWarnings("NullableProblems")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        // if everything is valid, set the authentication token and continue with the request
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        Collection<SimpleGrantedAuthority> roles = Set.of(new SimpleGrantedAuthority("ROLE_" + profile));
        // Return an authentication token containing the user and their authorities
        return new UsernamePasswordAuthenticationToken(
                "development",
                null,
                roles
        );
    }
}
