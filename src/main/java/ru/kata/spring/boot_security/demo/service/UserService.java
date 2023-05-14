package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    public List<User> getAllUsers();
    public void saveUser(User user);
    public User getUser(Long id);
    void deleteUser(Long id);
    public void updateUser(User user);
    public User findByName(String email);
    void addRole(Role role);
   public List<Role> getRoles();

}
