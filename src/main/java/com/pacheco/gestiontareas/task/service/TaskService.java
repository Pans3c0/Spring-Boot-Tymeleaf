package com.pacheco.gestiontareas.task.service;

import com.pacheco.gestiontareas.category.model.Category;
import com.pacheco.gestiontareas.category.model.CategoryRepository;
import com.pacheco.gestiontareas.tag.service.TagService;
import com.pacheco.gestiontareas.task.dto.CreateTaskRequest;
import com.pacheco.gestiontareas.task.dto.EditTaskRequest;
import com.pacheco.gestiontareas.task.exception.EmptyTaskListException;
import com.pacheco.gestiontareas.task.exception.TaskNotFoundException;
import com.pacheco.gestiontareas.task.model.Task;
import com.pacheco.gestiontareas.task.model.TaskRepository;
import com.pacheco.gestiontareas.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Servicio principal de la lógica de negocio de tareas.
 * Gestiona la creación, edición, eliminación y listado de tareas,
 * así como el toggle de estado completado y la reasignación de categorías.
 */
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final TagService tagService;

    /**
     * Método interno que busca todas las tareas.
     * Si se pasa un usuario, filtra por autor. Si no, devuelve todas.
     * Lanza EmptyTaskListException si no hay tareas.
     */
    private List<Task> findAll(User user) {

        List<Task> result = null;

        if (user != null)
            result = taskRepository.findByAuthor(user, Sort.by("createdAt").ascending());
        else
            result = taskRepository.findAll(Sort.by("createdAt").ascending());

        if (result.isEmpty())
            throw new EmptyTaskListException();

        return result;
    }

    /** Devuelve las tareas del usuario autenticado. */
    public List<Task> findAllByUser(User user) {
        return findAll(user);
    }

    /** Devuelve todas las tareas (vista de administrador). */
    public List<Task> findAllAdmin() {
        return findAll(null);
    }

    /**
     * Busca una tarea por su ID.
     * @throws TaskNotFoundException si no se encuentra la tarea
     */
    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    /** Crea una nueva tarea a partir del formulario de creación. */
    public Task createTask(CreateTaskRequest req, User author) {
        return createOrEditTask(req, author);
    }

    /** Edita una tarea existente a partir del formulario de edición. */
    public Task editTask(EditTaskRequest req) {
        return createOrEditTask(req, null);
    }

    /**
     * Método interno que maneja tanto la creación como la edición de tareas.
     * Utiliza herencia de DTOs (CreateTaskRequest / EditTaskRequest).
     * Se encarga de asignar la categoría y procesar las etiquetas.
     */
    private Task createOrEditTask(CreateTaskRequest req, User author) {

        Task task = Task.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .build();

        // Si no se selecciona categoría, se asigna la principal (id=1)
        if (req.getCategoryId() == null || req.getCategoryId() == -1L)
            req.setCategoryId(1L);
        Category category = categoryRepository.getReferenceById(req.getCategoryId());
        if (category == null)
            category = categoryRepository.getReferenceById(1L);

        task.setCategory(category);

        // Procesamos los tags que vienen en formato "tag1,tag2,tag3"
        List<String> textTags = Arrays.stream(req.getTags().split(","))
                .map(String::trim)
                .toList();
        task.getTags().addAll(tagService.saveOrGet(textTags));

        // Si es una edición, recuperamos los datos originales de la tarea
        if (req instanceof EditTaskRequest editReq) {
            Task oldTask = findById(editReq.getId());
            task.setId(oldTask.getId());
            task.setCreatedAt(oldTask.getCreatedAt());
            task.setAuthor(oldTask.getAuthor());
            task.setCompleted(editReq.isCompleted());
        } else {
            task.setAuthor(author);
        }

        // save() de JPA inserta si no tiene ID, o actualiza si ya lo tiene
        return taskRepository.save(task);

    }

    /** Cambia el estado de completado de una tarea (toggle). */
    public Task toggleComplete(Long id) {
        Task task = findById(id);
        task.setCompleted(!task.isCompleted());
        return taskRepository.save(task);
    }

    /** Elimina una tarea por su ID. */
    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    /**
     * Reasigna todas las tareas de una categoría a otra.
     * Se usa cuando se elimina una categoría.
     */
    public List<Task> updateCategory(Category oldCategory, Category newCategory) {
        List<Task> tasks = taskRepository.findByCategory(oldCategory);
        tasks.forEach(t -> t.setCategory(newCategory));
        taskRepository.saveAll(tasks);
        return tasks;
    }

}
