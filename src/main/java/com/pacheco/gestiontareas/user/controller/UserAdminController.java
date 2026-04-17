package com.pacheco.gestiontareas.user.controller;

import com.pacheco.gestiontareas.user.dto.UserResponse;
import com.pacheco.gestiontareas.user.model.UserRole;
import com.pacheco.gestiontareas.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class UserAdminController {

    private final UserService userService;

    @GetMapping("/admin/user")
    public String listUsers(Model model) {
        model.addAttribute("userList",
                userService.findAll().stream().map(UserResponse::of).toList());
        return "admin/admin-users";
    }

    // Cambiar el rol del usuario a ADMIN
    @GetMapping("/admin/user/{id}/promote")
    public String makeAdmin(@PathVariable Long id, Model model) {
        userService.changeRole(id, UserRole.ADMIN);
        return "redirect:/admin/user";
    }

}
