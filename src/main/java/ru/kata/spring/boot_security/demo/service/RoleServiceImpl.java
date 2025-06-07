package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleDao;


import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Transactional(readOnly = true)
    public Role findRoleById(Long id) {
        return roleDao.findRoleById(id);
    }


    @Transactional
    public void saveRole(Role role) {
        roleDao.saveRole(role);
    }

    @Transactional
    public Role findRoleByName(String name) {
        return roleDao.findRoleByName(name);
    }
}
