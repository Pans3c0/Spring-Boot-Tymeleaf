package com.pacheco.gestiontareas.task.exception;

import jakarta.persistence.EntityNotFoundException;

/**
 * Excepción que se lanza cuando no se encuentra una tarea por su ID.
 * Extiende EntityNotFoundException de JPA para mantener consistencia.
 */
public class TaskNotFoundException extends EntityNotFoundException {

    public TaskNotFoundException(String message) {
        super(message);
    }

    public TaskNotFoundException(Long id) {
        super("No se ha encontrado la tarea con ID: %d".formatted(id));
    }
}
