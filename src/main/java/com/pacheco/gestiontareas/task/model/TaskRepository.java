package com.pacheco.gestiontareas.task.model;

import com.pacheco.gestiontareas.category.model.Category;
import com.pacheco.gestiontareas.user.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByAuthor(User user, Sort sort);

    List<Task> findByCategory(Category category);

}
