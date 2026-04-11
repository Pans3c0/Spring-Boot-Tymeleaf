package com.pacheco.gestiontareas.tag.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio JPA para las etiquetas.
 * Permite buscar etiquetas por su texto para evitar duplicados.
 */
public interface TagRepository extends JpaRepository<Tag, Long> {

    /** Busca una etiqueta por su texto exacto. */
    Optional<Tag> findByText(String text);
}
