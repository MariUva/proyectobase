package com.proyectoback.backend.infrastructure.persistence.repository;


import com.proyectoback.backend.infrastructure.persistence.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserData, Long> {

    Optional<UserData> findByEmail(String username);

}
