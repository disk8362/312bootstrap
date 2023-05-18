package ru.kata.spring.boot_security.demo.create;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class Create {

    private final UserService userService;

    @Autowired
    public Create(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    private void createUsers() {
        Role adminRole = new Role("ADMIN");
        Role userRole = new Role("USER");
        Set<Role> setAdmin = new HashSet<>();
        Set<Role> setUser = new HashSet<>();
        userService.addRole(adminRole);
        userService.addRole(userRole);
        setAdmin.add(adminRole);
        setAdmin.add(userRole);
        setUser.add(userRole);
        User admin = new User("Ivan", "Ivanov", "ivan@mail.com", 35, "21", setAdmin);
        User newUser = new User("Petr", "Petrov", "petr@gmail.com", 20, "21", setUser);
        userService.saveUser(admin);
        userService.saveUser(newUser);
    }
}
