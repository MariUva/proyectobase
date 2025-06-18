package com.proyectoback.backend.infrastructure.dto.user;

public record RegisterRequest(String email, String password, String role) {}
