package com.proyectoback.backend.infrastructure.persistence.repository;

import com.proyectoback.backend.domain.model.User;
import com.proyectoback.backend.domain.repository.UserRepository;
import com.proyectoback.backend.infrastructure.persistence.entity.RoleData;
import com.proyectoback.backend.infrastructure.persistence.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
                .map(UserMapper::toDomain);
    }

    @Override
    public List<User> findByRole(String role) {
        try {
            // Convertimos el String a Enum RoleData
            RoleData roleEnum = RoleData.valueOf(role.toUpperCase());

            return jpaUserRepository.findByRole(roleEnum).stream()
                    .map(UserMapper::toDomain)
                    .collect(Collectors.toList());

        } catch (IllegalArgumentException e) {
            throw new RuntimeException("El rol '" + role + "' no es válido. Usa ADMIN o USER.");
        }
    }




    @Override
    public User save(User user) {
        var saved = jpaUserRepository.save(UserMapper.toEntity(user));
        return UserMapper.toDomain(saved);
    }
}
