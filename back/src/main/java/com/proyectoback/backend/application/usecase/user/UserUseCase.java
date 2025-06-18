package com.proyectoback.backend.application.usecase.user;

import com.proyectoback.backend.domain.model.User;
import com.proyectoback.backend.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepository;

    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getByRole(String role) {
        return userRepository.findByRole(role);
    }

    public User create(User user) {
        return userRepository.save(user);
    }
}
