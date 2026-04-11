package com.pacheco.gestiontareas.task.model;

import com.pacheco.gestiontareas.category.model.Category;
import com.pacheco.gestiontareas.user.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio JPA para la entidad Task.
 * Incluye consultas derivadas para filtrar por autor y categoría.
 */
public interface TaskRepository extends JpaRepository<Task, Long> {

    /** Busca las tareas de un usuario concreto, con posibilidad de ordenación. */
    List<Task> findByAuthor(User user, Sort sort);

    /** Busca las tareas que pertenecen a una categoría concreta. */
    List<Task> findByCategory(Category category);

}
