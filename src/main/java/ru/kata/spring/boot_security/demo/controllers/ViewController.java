package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
public class ViewController {
    private final UserService userService;

    @Autowired
    public ViewController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String admin(ModelMap model, Principal principal) {
        User authenticatedUser = userService.findUserByName(principal.getName());
        model.addAttribute("authenticatedUserRoles", authenticatedUser.getRoles());
        return "admin";
    }
    @GetMapping("/adminUser")
    public String adminUser(ModelMap model, Principal principal) {
        User authenticatedUser = userService.findUserByName(principal.getName());
        model.addAttribute("authenticatedUserRoles", authenticatedUser.getRoles());
        return "adminUser";
    }


    @GetMapping("/user")
    public String user(ModelMap model, Principal principal) {
        User authenticatedUser = userService.findUserByName(principal.getName());
        model.addAttribute("authenticatedUserRoles", authenticatedUser.getRoles());
        return "user";
    }
}
