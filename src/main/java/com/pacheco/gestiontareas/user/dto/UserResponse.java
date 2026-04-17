package com.pacheco.gestiontareas.user.dto;

import com.pacheco.gestiontareas.user.model.User;

/**
 * Record de respuesta para mostrar datos de usuario sin exponer información sensible.
 * Se utiliza en el panel de administración para listar usuarios.
 */
public record UserResponse(
        Long id,
        String username,
        String fullname,
        String email,
        String role
) {

    /**
     * Convierte una entidad User en un UserResponse.
     * @param user entidad de usuario
     * @return DTO con los datos públicos del usuario
     */
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
