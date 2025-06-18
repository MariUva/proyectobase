package com.proyectoback.backend.infrastructure.persistence.repository;


import com.proyectoback.backend.infrastructure.persistence.entity.RoleData;
import com.proyectoback.backend.infrastructure.persistence.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<UserData, Long> {

    Optional<UserData> findByEmail(String username);
    List<UserData> findByRole(RoleData role);

}
