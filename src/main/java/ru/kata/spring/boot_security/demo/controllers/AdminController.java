package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repositories.RoleDao;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
public class AdminController {
    private final UserService userService;
    private final RoleDao roleDao;

    @Autowired
    public AdminController(UserService userService, RoleDao roleDao) {
        this.userService = userService;
        this.roleDao = roleDao;
    }

    @GetMapping("/admin")
    public String admin(Model model, Principal principal,
                        @ModelAttribute("user") User newUser) {
        User user = userService.findUserByName(principal.getName());
        model.addAttribute("currentUser", user);
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("roles", user.getRoles());
        return "admin";
    }
    @PostMapping("/admin/new_user")
    public String saveUser(@ModelAttribute User user,
                           @RequestParam("roleNames") Set<String> selectedRoles) {
        Set<Role> roles = new HashSet<>();
        if (!selectedRoles.isEmpty()) {
            Arrays.stream(selectedRoles.toArray()).forEach(roleName -> roles.add(roleDao.findRoleByName(roleName.toString())));
        }
        user.setRoles(roles);
        userService.saveUser(user.getUsername(), user.getPassword(), user.getEmail(), user.getRoles());
        return "redirect:/admin";
    }

    @PostMapping("/admin/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @PostMapping("/admin/edit")
    public String editUser(@ModelAttribute User user,
                           @RequestParam("rolesName") Set<String> selectedRoles , @RequestParam("id") Long id) {
        Set<Role> roles = new HashSet<>();
        Arrays.stream(selectedRoles.toArray()).forEach(roleName -> roles.add(roleDao.findRoleByName(roleName.toString())));
        user.setRoles(roles);
        userService.updateUser(id, user.getUsername(), user.getPassword(), user.getEmail(), user.getRoles());
        return "redirect:/admin";
    }

    @PostMapping("/admin/findByID")
    public String findByName(@RequestParam Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findUserByName(userDetails.getUsername());
        model.addAttribute("currentUser", user);
        model.addAttribute("userFound", userService.findUserById(id));
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

}
