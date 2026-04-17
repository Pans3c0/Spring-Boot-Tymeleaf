package com.pacheco.gestiontareas.user.service;

import com.pacheco.gestiontareas.user.dto.CreateUserRequest;
import com.pacheco.gestiontareas.user.model.User;
import com.pacheco.gestiontareas.user.model.UserRepository;
import com.pacheco.gestiontareas.user.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio que gestiona la lógica de negocio de los usuarios.
 * Incluye registro, cambio de rol y listado.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    /**
     * Registra un nuevo usuario con rol USER por defecto.
     * La contraseña se encripta antes de almacenarla.
     */
    public User registerUser(CreateUserRequest request) {

        return userRepository.save(
                User.builder()
                        .username(request.getUsername())
                        .password(encoder.encode(request.getPassword()))
                        .email(request.getEmail())
                        .fullname(request.getFullname())
                        .role(UserRole.USER)
                        .build()
        );

    }

    /** Cambia el rol de un usuario ya existente (pasando el objeto User). */
    public User changeRole(User user, UserRole userRole) {
        user.setRole(userRole);
        return userRepository.save(user);
    }

    /** Cambia el rol de un usuario buscándolo por su ID. */
    public User changeRole(Long userId, UserRole userRole) {
        return userRepository.findById(userId)
                .map(u -> {
                    u.setRole(userRole);
                    return userRepository.save(u);
                }).orElse(null);
    }

    /** Devuelve todos los usuarios ordenados por nombre de usuario. */
    public List<User> findAll() {
        return userRepository.findAll(Sort.by("username"));
    }

}
