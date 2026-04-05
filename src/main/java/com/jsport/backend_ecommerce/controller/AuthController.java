package com.jsport.backend_ecommerce.controller;

import com.jsport.backend_ecommerce.dto.AuthResponse;
import com.jsport.backend_ecommerce.dto.LoginRequest;
import com.jsport.backend_ecommerce.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        // 1. Autenticación
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // 2. Generar el token
        String token = jwtService.generateToken(request.getEmail());

        // 3. Devolver el objeto AuthResponse envuelto en un ResponseEntity
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
