package com.proyectoback.backend.infrastructure.config;


import com.proyectoback.backend.domain.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;


    /*
     * Método que se ejecuta automáticamente en cada petición HTTP entrante.
     * Su objetivo es validar el token JWT y establecer la autenticación en el contexto de seguridad.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Si la ruta es pública (como /auth/login o /auth/register), no se valida el token
        String path = request.getServletPath();
        if (path.startsWith("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extracción y validación del token JWT desde la cabecera Authorization
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        // Si el header está presente y contiene un token Bearer, lo extraemos
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            email = jwtUtil.extractUsername(token);
        }
        System.out.println("Filtro JWT: interceptando " + request.getMethod() + " " + request.getRequestURI());

        // Si el usuario no está autenticado aún y se extrajo un email válido
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            // Validamos que el token no esté vencido ni sea inválido
            if (jwtUtil.validateToken(token)) {
                // Creamos un objeto de autenticación con los roles del usuario
                var role = "ROLE_" + user.getRole().name();
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                List.of(new SimpleGrantedAuthority(role))
                        );
                // Asociamos los detalles de la petición al token de autenticación
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Establecemos la autenticación para esta petición en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
