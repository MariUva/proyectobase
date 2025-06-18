package com.proyectoback.backend.infrastructure.controller;

import com.proyectoback.backend.application.usecase.auth.AuthService;
import com.proyectoback.backend.infrastructure.dto.auth.AuthRequest;
import com.proyectoback.backend.infrastructure.dto.auth.AuthResponse;
import com.proyectoback.backend.infrastructure.dto.user.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/register/admin")
    public AuthResponse registerAdmin(@RequestBody RegisterRequest request) {
        return authService.registerAdmin(request);
    }
}
