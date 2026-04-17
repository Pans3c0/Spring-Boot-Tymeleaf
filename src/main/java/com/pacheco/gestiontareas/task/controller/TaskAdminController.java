package com.pacheco.gestiontareas.task.controller;

import com.pacheco.gestiontareas.category.model.Category;
import com.pacheco.gestiontareas.category.service.CategoryService;
import com.pacheco.gestiontareas.task.dto.EditTaskRequest;
import com.pacheco.gestiontareas.task.model.Task;
import com.pacheco.gestiontareas.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador de administración de tareas.
 * Permite al admin ver y eliminar cualquier tarea del sistema.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/")
public class TaskAdminController {

    private final TaskService taskService;
    private final CategoryService categoryService;

    /** Categorías disponibles para los formularios de admin. */
    @ModelAttribute("categories")
    public List<Category> categories() {
        return categoryService.findAll();
    }

    /** Lista todas las tareas de todos los usuarios (vista admin). */
    @GetMapping({"/", "/list", "/task"})
    public String adminTaskList(Model model) {
        model.addAttribute("taskList", taskService.findAllAdmin());
        return "admin/admin-tasks";
    }

    /** Manejo cuando la lista de tareas está vacía. */
    @GetMapping(value = {"/", "/list", "/task"}, params = "emptyListError")
    public String adminEmptyList(Model model) {
        return "admin/admin-tasks";
    }

    /** Elimina una tarea desde el panel de administración. */
    @PostMapping("/task/{id}/del")
    public String adminDeleteTask(@PathVariable Long id) {
        taskService.deleteById(id);
        return "redirect:/admin/";
    }

    /** Muestra los detalles de una tarea (solo lectura). */
    @GetMapping("/task/{id}")
    public String adminViewTask(@PathVariable Long id, Model model) {
        Task task = taskService.findById(id);
        EditTaskRequest editTask = EditTaskRequest.of(task);
        model.addAttribute("task", editTask);
        return "admin/view-task";
    }

}
