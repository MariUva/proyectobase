// domain/model/User.java
package com.proyectoback.backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class User {
    private Long id;
    private String email;
    private String password;
    private Role role;
}
