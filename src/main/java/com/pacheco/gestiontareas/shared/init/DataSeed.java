package com.pacheco.gestiontareas.shared.init;

import com.pacheco.gestiontareas.category.model.Category;
import com.pacheco.gestiontareas.category.model.CategoryRepository;
import com.pacheco.gestiontareas.task.dto.CreateTaskRequest;
import com.pacheco.gestiontareas.task.service.TaskService;
import com.pacheco.gestiontareas.user.dto.CreateUserRequest;
import com.pacheco.gestiontareas.user.model.User;
import com.pacheco.gestiontareas.user.model.UserRole;
import com.pacheco.gestiontareas.user.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Componente que carga datos iniciales en la base de datos al arrancar.
 * Crea categorías por defecto, usuarios de prueba y tareas de ejemplo.
 * Solo se ejecuta la primera vez que se arranca la aplicación.
 */
@Component
@RequiredArgsConstructor
public class DataSeed {

    private final CategoryRepository categoryRepository;
    private final TaskService taskService;
    private final UserService userService;

    /**
     * Método que se ejecuta automáticamente después de la inyección
     * de dependencias (@PostConstruct).
     */
    @PostConstruct
    public void init() {
        insertCategories();
        List<User> users = insertUsers();
        insertTasks(users.get(0));
    }

    /**
     * Crea los usuarios de prueba.
     * Solo devuelve los que tienen rol USER para usarlos como autores.
     */
    private List<User> insertUsers() {

        List<User> result = new ArrayList<>();

        // Usuario normal de prueba
        CreateUserRequest req = CreateUserRequest.builder()
                .username("user")
                .email("usuario@prueba.com")
                .password("1234")
                .verifyPassword("1234")
                .fullname("Usuario de Prueba")
                .build();
        User user = userService.registerUser(req);
        result.add(user);

        // Usuario administrador
        CreateUserRequest req2 = CreateUserRequest.builder()
                .username("admin")
                .email("admin@gestiontareas.com")
                .password("1234")
                .verifyPassword("1234")
                .fullname("Administrador")
                .build();
        User user2 = userService.registerUser(req2);

        userService.changeRole(user2, UserRole.ADMIN);

        return result;
    }

    /** Crea la categoría principal por defecto. */
    private void insertCategories() {
        categoryRepository.save(Category.builder().title("Trabajo").build());
        categoryRepository.save(Category.builder().title("Personal").build());
    }

    /** Crea tareas de ejemplo para demostrar la funcionalidad. */
    private void insertTasks(User author) {

        CreateTaskRequest req1 = CreateTaskRequest.builder()
                .title("Completar proyecto DAW")
                .description("Terminar la aplicación de gestión de tareas con Spring Boot")
                .tags("spring,daw,proyecto")
                .build();

        taskService.createTask(req1, author);

        CreateTaskRequest req2 = CreateTaskRequest.builder()
                .title("Estudiar para el examen")
                .description("Repasar los temas de acceso a datos y desarrollo web")
                .tags("estudio,examen,daw")
                .build();

        taskService.createTask(req2, author);

    }
}
