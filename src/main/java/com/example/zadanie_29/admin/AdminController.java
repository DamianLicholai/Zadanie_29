package com.example.zadanie_29.admin;

import com.example.zadanie_29.user.User;
import com.example.zadanie_29.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/admin")
@Controller
public class AdminController {

    private UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String adminPanel(Model model) {

        List<User> users = userService.findAllWithoutCurrentUser();

        model.addAttribute("users", users);

        return "admin";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable Long id) {

         userService.deleteUserById(id);

        return "redirect:/admin";
    }

    @GetMapping("/changeUserRoles/{id}")
    public String addUserAdminRole(@PathVariable Long id) {

        userService.addAdminUserRoleByID(id);

        return "redirect:/admin";
    }

    @GetMapping("/deleteUserRolesAdmin/{id}")
    public String deleteUserAdminRole(@PathVariable Long id) {

        userService.deleteAdminUserRoleByID(id);

        return "redirect:/admin";
    }

}
