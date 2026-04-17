# MisTareas - Aplicación de Gestión de Tareas

Aplicación web para la gestión de tareas personales desarrollada con **Spring Boot** y **Thymeleaf**.
Permite a los usuarios crear, editar, completar y eliminar tareas, organizándolas por categorías y etiquetas.
Incluye un sistema de autenticación con roles (usuario y administrador).

## Tecnologías utilizadas

- **Java 17**
- **Spring Boot 3.3.5**
- **Spring Security** - Autenticación y autorización basada en roles
- **Spring Data JPA** - Acceso a datos con Hibernate
- **Thymeleaf** - Motor de plantillas para las vistas
- **H2 Database** - Base de datos embebida (en memoria para desarrollo)
- **Lombok** - Reducción de código repetitivo
- **Bootstrap 5** - Framework CSS para el diseño responsive
- **Maven** - Gestión de dependencias y construcción
- **Docker** - Contenedorización de la aplicación

## Funcionalidades

### Usuario normal
- Registro e inicio de sesión
- Crear nuevas tareas con título, descripción, categoría y etiquetas
- Ver y editar tareas propias
- Marcar tareas como completadas
- Eliminar tareas

### Administrador
- Panel de administración con vista de todas las tareas
- Gestión de usuarios (promocionar a administrador)
- Gestión de categorías (crear y eliminar)
- Vista detallada de cualquier tarea del sistema

## Estructura del proyecto

```
src/main/java/com/pacheco/gestiontareas/
├── GestionTareasApplication.java       # Clase principal
├── category/                           # Módulo de categorías
│   ├── controller/
│   ├── model/
│   └── service/
├── task/                               # Módulo de tareas
│   ├── controller/
│   ├── dto/
│   ├── exception/
│   ├── model/
│   └── service/
├── user/                               # Módulo de usuarios
│   ├── controller/
│   ├── dto/
│   ├── model/
│   └── service/
├── tag/                                # Módulo de etiquetas
│   ├── model/
│   └── service/
└── shared/                             # Configuración compartida
    ├── config/
    ├── error/
    ├── init/
    └── security/
```

## Cómo ejecutar

### Requisitos previos
- Java 17 o superior
- Maven 3.8+

### Ejecución local

```bash
# Clonar el repositorio
git clone <url-del-repositorio>

# Entrar en la carpeta del proyecto
cd gestion-tareas

# Compilar y ejecutar
mvn spring-boot:run
```

La aplicación estará disponible en: **http://localhost:8080**

### Con Docker

```bash
# Construir y arrancar con Docker Compose
docker compose up --build
```

## Datos de acceso por defecto

La aplicación carga datos de prueba automáticamente al iniciar:

| Usuario | Contraseña | Rol |
|---------|-----------|-----|
| `user`  | `1234`    | USER |
| `admin` | `1234`    | ADMIN |

## Consola H2

Para acceder a la consola de la base de datos H2:
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:test`
- Usuario: `sa`
- Contraseña: *(vacía)*

## Arquitectura

El proyecto sigue una arquitectura por capas organizada por **módulos funcionales**:

- **Model**: Entidades JPA que representan las tablas de la base de datos
- **Repository**: Interfaces que extienden JpaRepository para el acceso a datos
- **Service**: Capa de lógica de negocio
- **Controller**: Controladores MVC que gestionan las peticiones HTTP
- **DTO**: Objetos de transferencia de datos para los formularios

La seguridad está implementada con Spring Security, usando autenticación basada en formulario
y autorización por roles (USER/ADMIN).

---

*Proyecto de 2º DAW - Desarrollo Web en Entorno Servidor - 2026*
