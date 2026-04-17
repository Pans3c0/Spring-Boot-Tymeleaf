package com.pacheco.gestiontareas.category.controller;

import com.pacheco.gestiontareas.category.model.Category;
import com.pacheco.gestiontareas.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador de administración de categorías.
 * Permite crear y eliminar categorías desde el panel de admin.
 */
@Controller
@RequestMapping("/admin/")
@RequiredArgsConstructor
public class CategoryAdminController {

    private final CategoryService categoryService;

    /** Muestra el listado de categorías y el formulario para crear una nueva. */
    @GetMapping("/category")
    public String showCategories(Model model) {
        model.addAttribute("categoryList", categoryService.findAll());
        model.addAttribute("newCategory", new Category());
        return "admin/admin-categories";
    }

    /** Procesa el formulario de creación de categoría. */
    @PostMapping("/category/submit")
    public String processNewCategory(@ModelAttribute("newCategory") Category newCategory, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "admin/admin-categories";

        categoryService.save(newCategory);
        return "redirect:/admin/category";
    }

    /** Elimina una categoría por su ID (no se puede borrar la principal). */
    @PostMapping("/category/{id}/del")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return "redirect:/admin/category";
    }

}
