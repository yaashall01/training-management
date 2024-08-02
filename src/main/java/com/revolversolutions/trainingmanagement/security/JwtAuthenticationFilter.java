package com.revolversolutions.trainingmanagement.security;


import com.revolversolutions.trainingmanagement.entity.User;
import com.revolversolutions.trainingmanagement.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    public JwtAuthenticationFilter(JwtService jwtService, @Lazy UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }



    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");


        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("JWT Token does not begin with Bearer String or is null");
            filterChain.doFilter(request, response);
            return;
        }
        String token = authHeader.substring(7);
        String emailOrUsername;
        try {
            emailOrUsername = jwtService.extractEmail(token);  // Change this line to extract email instead of username
        } catch (Exception e) {
            log.error("Error extracting email from token", e);
            filterChain.doFilter(request, response);
            return;
        }

        if (emailOrUsername != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User userDetails;
            try {
                userDetails = userService.loadUserByUsername(emailOrUsername);  // Change this to load user by email
            } catch (Exception e) {
                log.error("User not found with email: {}", emailOrUsername, e);
                filterChain.doFilter(request, response);
                return;
            }

            if (jwtService.isValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
                log.info("Successfully authenticated user with email or username: {}", emailOrUsername);
            } else {
                log.warn("Invalid JWT Token for user with email or username: {}", emailOrUsername);
            }
        }
        filterChain.doFilter(request, response);
    }
}
