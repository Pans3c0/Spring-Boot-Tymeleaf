package com.pacheco.gestiontareas.category.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

/**
 * Entidad JPA que representa una categoría para clasificar las tareas.
 * Cada tarea pertenece a una categoría (relación ManyToOne).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class Category {

    @Id
    @GeneratedValue
    private Long id;

    /** Nombre de la categoría (ej: "Trabajo", "Personal", "Estudios") */
    private String title;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Category category = (Category) o;
        return getId() != null && Objects.equals(getId(), category.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
