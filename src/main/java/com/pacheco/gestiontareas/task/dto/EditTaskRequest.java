package com.pacheco.gestiontareas.task.dto;

import com.pacheco.gestiontareas.tag.model.Tag;
import com.pacheco.gestiontareas.task.model.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * DTO para la edición de una tarea existente.
 * Extiende CreateTaskRequest añadiendo campos de solo lectura
 * como el ID, la fecha de creación y el nombre del autor.
 */
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class EditTaskRequest extends CreateTaskRequest {

    private Long id;
    private boolean completed;
    private LocalDateTime createdAt;
    private String username;

    /**
     * Método factory que convierte una entidad Task en un EditTaskRequest.
     * Las etiquetas se convierten a una cadena separada por comas.
     *
     * @param task la entidad de tarea
     * @return DTO con todos los datos de la tarea
     */
    public static EditTaskRequest of(Task task) {
        return EditTaskRequest.builder()
                .id(task.getId())
                .completed(task.isCompleted())
                .createdAt(task.getCreatedAt())
                .username(task.getAuthor().getFullname())
                .title(task.getTitle())
                .description(task.getDescription())
                .categoryId(task.getCategory().getId())
                .tags(task.getTags().stream().map(Tag::getText).collect(Collectors.joining(", ")))
                .build();
    }

}
