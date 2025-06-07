package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.repositories.UserDao;

import java.util.List;

public interface RoleService {
    Role findRoleById(Long id);
    Role findRoleByName(String roleName);
    void saveRole(Role role);
}
