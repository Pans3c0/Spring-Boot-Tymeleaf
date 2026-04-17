package com.pacheco.gestiontareas.shared.error;

import com.pacheco.gestiontareas.task.exception.EmptyTaskListException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Manejador global de excepciones con @ControllerAdvice.
 * Captura las excepciones lanzadas desde cualquier controlador
 * y las gestiona de forma centralizada.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Captura EmptyTaskListException y redirige al usuario
     * a la vista principal con un parámetro que indica que la lista está vacía.
     */
    @ExceptionHandler(EmptyTaskListException.class)
    public String emptyTaskList(EmptyTaskListException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("emptyListError", true);
        return "redirect:/";
    }

}
