package com.revolversolutions.trainingmanagement.controller;


import com.revolversolutions.trainingmanagement.dto.AuthenticationResponse;
import com.revolversolutions.trainingmanagement.dto.LoginUserDto;
import com.revolversolutions.trainingmanagement.dto.RegisterUserDto;
import com.revolversolutions.trainingmanagement.serviceImpl.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterUserDto request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody LoginUserDto request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<?> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return authService.refreshToken(request, response);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Logged out successfully");
    }



}
