package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService, UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> existingRoles = new HashSet<>();
        for (Role role : user.getRoles()) {
            Role existingRole = roleDao.find(role.getId());
            if (existingRole != null) {
                existingRoles.add(existingRole);
            }
        }
        user.setRoles(existingRoles);
        userDao.saveUser(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> existingRoles = new HashSet<>();
        for (Role role : user.getRoles()) {
            Role existingRole = roleDao.find(role.getId());
            if (existingRole != null) {
                existingRoles.add(existingRole);
            }
        }
        user.setRoles(existingRoles);
        userDao.updateUser(user);
    }

    @Override
    @Transactional
    public User getUser(Long id) {
        return userDao.getUser(id);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    public User findByName(String email) {
        return userDao.findByName(email);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findByName(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User not found", email));
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addRole(Role role) {
        roleDao.addRole(role);
    }

    @Override
    public List<Role> getRoles() {
        return roleDao.getRoles();
    }

    @Override
    public Role find(Long id) {
        return roleDao.find(id);
    }
}
