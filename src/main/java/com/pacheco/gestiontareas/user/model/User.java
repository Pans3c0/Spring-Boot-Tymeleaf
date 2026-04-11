package com.pacheco.gestiontareas.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Entidad JPA que representa un usuario del sistema.
 * Implementa UserDetails de Spring Security para integrarse
 * con el sistema de autenticación.
 *
 * La tabla se llama "user_entity" porque "user" es palabra reservada en H2.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "user_entity")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    private String username, password, email, fullname;

    /** Rol del usuario (USER o ADMIN) */
    private UserRole role;

    /**
     * Devuelve las autoridades del usuario para Spring Security.
     * Se utiliza el prefijo "ROLE_" que es el estándar de Spring.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

}
