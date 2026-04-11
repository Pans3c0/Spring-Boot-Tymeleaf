package com.pacheco.gestiontareas.task.model;

import com.pacheco.gestiontareas.category.model.Category;
import com.pacheco.gestiontareas.tag.model.Tag;
import com.pacheco.gestiontareas.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Entidad JPA que representa una tarea.
 * Cada tarea tiene un título, descripción, estado de completado,
 * fecha de creación, autor, categoría y etiquetas asociadas.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class Task {

    @Id
    @GeneratedValue
    private Long id;

    /** Título de la tarea (campo obligatorio) */
    private String title;

    /** Descripción detallada de la tarea (puede ser largo, por eso @Lob) */
    @Lob
    private String description;

    /** Indica si la tarea está completada o pendiente */
    private boolean completed;

    /** Fecha y hora de creación de la tarea (se asigna automáticamente) */
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * Relación ManyToMany con las etiquetas.
     * Se usa EAGER para cargar las etiquetas junto con la tarea.
     * La tabla intermedia se llama "task_tag".
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "task_tag",
            joinColumns = @JoinColumn(name = "task_id"),
            foreignKey = @ForeignKey(name = "fk_task_tag_task"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"),
            inverseForeignKey = @ForeignKey(name = "fk_task_tag_tag")
    )
    @Builder.Default
    @Setter(AccessLevel.NONE)
    private Set<Tag> tags = new HashSet<>();

    /** Usuario que creó la tarea */
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_task_user"))
    private User author;

    /** Categoría a la que pertenece la tarea */
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_task_category"))
    private Category category;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Task task = (Task) o;
        return getId() != null && Objects.equals(getId(), task.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
