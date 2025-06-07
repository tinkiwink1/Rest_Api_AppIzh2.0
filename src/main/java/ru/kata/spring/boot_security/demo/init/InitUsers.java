package ru.kata.spring.boot_security.demo.init;


import org.springframework.stereotype.Component;

import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class InitUsers {
    private final UserService userService;
    private final RoleService roleService;

    public InitUsers(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void init() {
        Role adminRole;
        Role userRole;
        if(roleService.findRoleByName("ROLE_ADMIN") == null) {
            adminRole = new Role("ROLE_ADMIN");
            roleService.saveRole(adminRole);
        }
        if(roleService.findRoleByName("ROLE_USER") == null) {
            userRole = new Role("ROLE_USER");
            roleService.saveRole(userRole);
        }

        User admin = userService.findUserByName("admin");
        if (admin == null) {
            adminRole = roleService.findRoleByName("ROLE_ADMIN");
            userRole = roleService.findRoleByName("ROLE_USER");
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);
            adminRoles.add(userRole);
            admin = new User( "admin", "123","admin@kata.ru", adminRoles);
            userService.saveUser(admin);
        }
        User user = userService.findUserByName("user");
        if (user == null) {
            userRole = roleService.findRoleByName("ROLE_USER");
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(userRole);
            user = new User( "user", "123","user@kata.ru", adminRoles);
            userService.saveUser(user);
        }
    }
}
