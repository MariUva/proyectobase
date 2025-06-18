package com.proyectoback.backend.infrastructure.persistence.mapper;

import com.proyectoback.backend.domain.model.Role;
import com.proyectoback.backend.domain.model.User;
import com.proyectoback.backend.infrastructure.persistence.entity.RoleData;
import com.proyectoback.backend.infrastructure.persistence.entity.UserData;

public class UserMapper {

    public static User toDomain(UserData userEntity) {
        if (userEntity == null) return null;

        return User.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .role(mapRoleToDomain(userEntity.getRole()))
                .build();
    }

    public static UserData toEntity(User User) {
        if (User == null) return null;

        return UserData.builder()
                .id(User.getId())
                .email(User.getEmail())
                .password(User.getPassword())
                .role(mapRoleToEntity(User.getRole()))
                .build();
    }

    private static Role mapRoleToDomain(RoleData role) {
        return com.proyectoback.backend.domain.model.Role.valueOf(role.name());
    }

    private static RoleData mapRoleToEntity(com.proyectoback.backend.domain.model.Role role) {
        return RoleData.valueOf(role.name());
    }
}
