package com.proyectoback.backend.application.usecase.auth;

import com.proyectoback.backend.infrastructure.dto.auth.AuthRequest;
import com.proyectoback.backend.infrastructure.dto.auth.AuthResponse;
import com.proyectoback.backend.infrastructure.dto.user.RegisterRequest;

public interface AuthService {
    AuthResponse login(AuthRequest request);
    AuthResponse register(RegisterRequest request);
    AuthResponse registerAdmin(RegisterRequest request);
}
