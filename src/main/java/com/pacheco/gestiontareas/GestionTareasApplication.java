package com.pacheco.gestiontareas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación de gestión de tareas.
 * Utiliza Spring Boot para arrancar el servidor embebido.
 *
 * @author Pacheco
 */
@SpringBootApplication
public class GestionTareasApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionTareasApplication.class, args);
	}

}
