package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleDao {

    void addRole(Role role);
    public List<Role> getRoles();
    Role find(Long id);
}
