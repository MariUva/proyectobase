package com.proyectoback.backend.application.usecase.auth;

import com.proyectoback.backend.domain.model.Role;
import com.proyectoback.backend.domain.model.User;
import com.proyectoback.backend.domain.repository.UserRepository;
import com.proyectoback.backend.infrastructure.dto.auth.AuthRequest;
import com.proyectoback.backend.infrastructure.dto.auth.AuthResponse;
import com.proyectoback.backend.infrastructure.dto.user.RegisterRequest;
import com.proyectoback.backend.infrastructure.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        var user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String jwt = jwtUtil.generateToken(user);
        return new AuthResponse(jwt);
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
        String jwt = jwtUtil.generateToken(user);
        return new AuthResponse(jwt);
    }

    @Override
    public AuthResponse registerAdmin(RegisterRequest request) {
        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.ADMIN)
                .build();

        userRepository.save(user);
        String jwt = jwtUtil.generateToken(user);
        return new AuthResponse(jwt);
    }
}
