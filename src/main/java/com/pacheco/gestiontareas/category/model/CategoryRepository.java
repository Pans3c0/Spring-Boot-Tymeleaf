package com.pacheco.gestiontareas.category.model;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio JPA para gestionar las categorías.
 * Los métodos CRUD son heredados de JpaRepository.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
