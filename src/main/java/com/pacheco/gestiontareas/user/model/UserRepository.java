package com.pacheco.gestiontareas.user.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio JPA para la entidad User.
 * Spring Data genera la implementación automáticamente.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Busca un usuario por su nombre de usuario o su email.
     * Se usa en el login para permitir ambos métodos de acceso.
     */
    Optional<User> findByUsernameOrEmail(String username, String email);

}
