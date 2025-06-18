package com.proyectoback.backend.infrastructure.config;

import com.proyectoback.backend.domain.model.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    // Clave secreta usada para firmar el token
    private final SecretKey secretKey;

    // Tiempo de expiración del token, en milisegundos (configurable desde application.properties)
    @Value("${jwt.expiration}")
    private long expiration;

    /*
     * Constructor que inicializa la clave secreta a partir del valor inyectado de las propiedades.
     * Usa codificación HMAC SHA para firmar el token.
     */
    public JwtUtil(@Value("${jwt.secret}") String secret) {
        // Convierte el string a clave segura
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /*
     * Genera un token JWT para un usuario dado.
     * El token incluye el email como subject y el rol como "claim".
     */
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /*
     * Extrae el "subject" (email del usuario) a partir de un token JWT dado.
     */
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /*
     * Verifica que un token JWT sea válido (firma y expiración).
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
