package com.pacheco.gestiontareas.category.service;

import com.pacheco.gestiontareas.category.model.Category;
import com.pacheco.gestiontareas.category.model.CategoryRepository;
import com.pacheco.gestiontareas.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para la gestión de categorías.
 * Cuando se elimina una categoría, las tareas asociadas se reasignan
 * a la categoría principal (id=1) para no perder datos.
 */
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final TaskService taskService;

    /** Devuelve todas las categorías ordenadas alfabéticamente. */
    public List<Category> findAll() {
        return categoryRepository.findAll(Sort.by("title").ascending());
    }

    /**
     * Elimina una categoría por su ID.
     * La categoría con id=1 (principal) no se puede eliminar.
     * Las tareas de la categoría eliminada se mueven a la principal.
     */
    public void deleteById(Long id) {
        if (id != 1L) {
            Category oldCategory = categoryRepository.getReferenceById(id);
            Category mainCategory = categoryRepository.getReferenceById(1L);
            taskService.updateCategory(oldCategory, mainCategory);
            categoryRepository.deleteById(id);
        }
    }

    /** Guarda una nueva categoría en la base de datos. */
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

}
