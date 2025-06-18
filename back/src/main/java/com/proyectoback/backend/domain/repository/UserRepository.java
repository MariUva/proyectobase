package com.proyectoback.backend.domain.repository;

import com.proyectoback.backend.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);
    List<User> findByRole(String role);
    User save(User user);
}
