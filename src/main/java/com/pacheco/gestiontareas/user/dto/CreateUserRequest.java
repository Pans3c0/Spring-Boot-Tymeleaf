package com.pacheco.gestiontareas.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para el formulario de registro de nuevos usuarios.
 * Incluye verificación de contraseña (verifyPassword).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequest {

    private String username, password, verifyPassword, email, fullname;

}
