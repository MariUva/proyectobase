package com.proyectoback.backend.infrastructure.controller;


import com.proyectoback.backend.application.usecase.UserUseCase;
import com.proyectoback.backend.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserUseCase createUserUseCase;

    @GetMapping("/{email}")
    public ResponseEntity<User> getUser(@PathVariable String email) {
        Optional<User> user = createUserUseCase.getByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<User>> getByRole(@PathVariable String role) {
        List<User> users = createUserUseCase.getByRole(role);
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build(); // o notFound() si prefieres
        }
        return ResponseEntity.ok(users);
    }


    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User created = createUserUseCase.create(user);
        return ResponseEntity.ok(created);
    }
}
