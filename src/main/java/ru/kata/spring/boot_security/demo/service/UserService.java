package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAllUsers();

    User findUserById(Long id);

    User findUserByName (String username);

    void deleteUser(Long id);

    void saveUser(User user);

    void updateUser(User user);

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
