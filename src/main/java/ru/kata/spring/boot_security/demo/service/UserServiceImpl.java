package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.exception_handlers.NotSuchFoundUserException;
import ru.kata.spring.boot_security.demo.repositories.RoleDao;
import ru.kata.spring.boot_security.demo.repositories.RoleDaoImpl;
import ru.kata.spring.boot_security.demo.repositories.UserDao;


import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    Logger logger = Logger.getLogger(this.getClass().getName());

    public UserServiceImpl(UserDao userDao, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        User user = userDao.findUserById(id);
        if(user == null) {
            throw new NotSuchFoundUserException("User with id = " + id + " not found in Database");
        }
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByName(String username) {
        return userDao.findUserByName(username);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        if (userDao.findUserByName(user.getUsername()) == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(user.getRoles().stream()
                    .map(role -> roleService.findRoleById(role.getId())).collect(Collectors.toSet()));
            userDao.saveUser(user);
        }
    }
    @Override
    @Transactional
    public void updateUser(User user) throws NotSuchFoundUserException  {
        if (user.getPassword().isEmpty()) {
            user.setPassword(findUserById(user.getId()).getPassword());
        }
        else user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.updateUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findUserWithRolesByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
