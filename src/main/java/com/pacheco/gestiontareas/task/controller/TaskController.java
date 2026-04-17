package com.pacheco.gestiontareas.task.controller;

import com.pacheco.gestiontareas.category.model.Category;
import com.pacheco.gestiontareas.category.service.CategoryService;
import com.pacheco.gestiontareas.task.dto.CreateTaskRequest;
import com.pacheco.gestiontareas.task.dto.EditTaskRequest;
import com.pacheco.gestiontareas.task.model.Task;
import com.pacheco.gestiontareas.task.service.TaskService;
import com.pacheco.gestiontareas.user.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * Controlador principal de tareas para usuarios normales.
 * Gestiona el listado, creación, edición, toggle y borrado de tareas.
 */
@Controller
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final CategoryService categoryService;

    /**
     * Carga la lista de categorías para que esté disponible
     * en todos los formularios de este controlador.
     */
    @ModelAttribute("categories")
    public List<Category> categories() {
        return categoryService.findAll();
    }

    /** Muestra la lista de tareas del usuario autenticado. */
    @GetMapping({"/", "/list", "/task"})
    public String taskList(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("taskList", taskService.findAllByUser(user));
        model.addAttribute("newTask", new CreateTaskRequest());
        return "task-list";
    }

    /** Manejo cuando la lista está vacía (parámetro emptyListError). */
    @GetMapping(value = {"/", "/list", "/task"}, params = "emptyListError")
    public String createTask(Model model) {
        model.addAttribute("newTask", new CreateTaskRequest());
        return "task-list";
    }

    /** Procesa el formulario de creación de una nueva tarea. */
    @PostMapping("/task/submit")
    public String taskSubmit(
            @Valid @ModelAttribute("newTask") CreateTaskRequest req,
            BindingResult bindingResult,
            @AuthenticationPrincipal User author,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("taskList", taskService.findAllByUser(author));
            return "task-list";
        }

        taskService.createTask(req, author);
        return "redirect:/";
    }

    /** Muestra el formulario de edición de una tarea por su ID. */
    @GetMapping("/task/{id}")
    public String viewOrEditTask(@PathVariable Long id, Model model) {
        Task task = taskService.findById(id);
        EditTaskRequest editTask = EditTaskRequest.of(task);
        model.addAttribute("task", editTask);
        return "show-task";
    }

    /** Procesa el formulario de edición de una tarea existente. */
    @PostMapping("/task/edit/submit")
    public String taskEditSubmit(
            @Valid @ModelAttribute("task") EditTaskRequest req,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "show-task";
        }

        taskService.editTask(req);
        return "redirect:/";
    }

    /** Cambia el estado completado/pendiente de una tarea. */
    @GetMapping("/task/{id}/toggle")
    public String toggleTask(@PathVariable Long id) {
        taskService.toggleComplete(id);
        return "redirect:/";
    }

    /** Elimina una tarea por su ID. */
    @PostMapping("/task/{id}/del")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteById(id);
        return "redirect:/";
    }

}
