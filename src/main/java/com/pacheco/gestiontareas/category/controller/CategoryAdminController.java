package com.pacheco.gestiontareas.category.controller;

import com.pacheco.gestiontareas.category.model.Category;
import com.pacheco.gestiontareas.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/")
@RequiredArgsConstructor
public class CategoryAdminController {

    private final CategoryService categoryService;

    @GetMapping("/category")
    public String showCategories(Model model) {
        model.addAttribute("categoryList", categoryService.findAll());
        model.addAttribute("newCategory", new Category());
        return "admin/admin-categories";
    }

    @PostMapping("/category/submit")
    public String processNewCategory(@ModelAttribute("newCategory") Category newCategory, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "admin/admin-categories";

        categoryService.save(newCategory);
        return "redirect:/admin/category";
    }

    // El borrado gestiona que no se elimine la categoría por defecto
    @PostMapping("/category/{id}/del")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return "redirect:/admin/category";
    }

}
