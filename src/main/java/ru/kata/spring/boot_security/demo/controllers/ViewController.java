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
    public ModelAndView admin(ModelAndView model, Principal principal) {
        User authenticatedUser = userService.findUserByName(principal.getName());
        model.setViewName("admin");
        model.addObject("authenticatedUserRoles", authenticatedUser.getRoles());
        return model;
    }
    @GetMapping("/adminUser")
    public ModelAndView adminUser(ModelAndView model, Principal principal) {
        User authenticatedUser = userService.findUserByName(principal.getName());
        model.setViewName("adminUser");
        model.addObject("authenticatedUserRoles", authenticatedUser.getRoles());
        return model;
    }


    @GetMapping("/user")
    public ModelAndView user(ModelAndView model, Principal principal) {
        User authenticatedUser = userService.findUserByName(principal.getName());
        model.setViewName("user");
        model.addObject("authenticatedUserRoles", authenticatedUser.getRoles());
        return model;
    }
}
