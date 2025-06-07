package ru.kata.spring.boot_security.demo.repositories;

import ru.kata.spring.boot_security.demo.entity.Role;

import java.util.List;

public interface RoleDao {
    Role findRoleByName(String name);

    Role findRoleById(Long id);

    void saveRole(Role role);
}
