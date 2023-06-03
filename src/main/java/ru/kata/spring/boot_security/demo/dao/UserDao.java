package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {

    public List<User> getAllUsers();
    public void saveUser(User user);
    public User getUser(Long id);
    public void updateUser(User user);
    public void deleteUser(Long id);
    User findByName(String email);
}
