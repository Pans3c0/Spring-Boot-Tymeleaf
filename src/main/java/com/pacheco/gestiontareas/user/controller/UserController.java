package com.pacheco.gestiontareas.user.controller;

import com.pacheco.gestiontareas.user.dto.CreateUserRequest;
import com.pacheco.gestiontareas.user.model.User;
import com.pacheco.gestiontareas.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controlador para el registro de usuarios.
 * Gestiona el formulario de registro y el procesamiento del mismo.
 */
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /** Muestra el formulario de registro de usuario. */
    @GetMapping("/auth/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new CreateUserRequest());
        return "register";
    }

    /** Procesa el formulario de registro y crea el nuevo usuario. */
    @PostMapping("/auth/register/submit")
    public String processRegisterForm(
            @ModelAttribute("user") CreateUserRequest request,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "register";

        User saved = userService.registerUser(request);

        return "redirect:/login";
    }

}
