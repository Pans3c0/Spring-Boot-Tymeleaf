package com.pacheco.gestiontareas.user.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Buscar por username o por email
    Optional<User> findByUsernameOrEmail(String username, String email);

}
