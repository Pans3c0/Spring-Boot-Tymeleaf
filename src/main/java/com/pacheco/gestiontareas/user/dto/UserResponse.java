package com.pacheco.gestiontareas.user.dto;

import com.pacheco.gestiontareas.user.model.User;

public record UserResponse(
        Long id,
        String username,
        String fullname,
        String email,
        String role
) {

    // Mapper de entidad a record
    public static UserResponse of(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getFullname(),
                user.getEmail(),
                user.getRole().name()
        );
    }
}
