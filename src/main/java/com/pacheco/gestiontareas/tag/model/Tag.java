package com.pacheco.gestiontareas.tag.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

/**
 * Entidad JPA que representa una etiqueta (tag).
 * Las etiquetas se asignan a las tareas con una relación ManyToMany.
 * Permiten clasificar las tareas de forma flexible.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class Tag {

    @Id
    @GeneratedValue
    private Long id;

    /** Texto de la etiqueta (ej: "urgente", "trabajo", "personal") */
    private String text;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Tag tag = (Tag) o;
        return getId() != null && Objects.equals(getId(), tag.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
