package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class RoleDaoHibernateImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    public RoleDaoHibernateImpl() {
    }

    @Override
    public void addRole(Role role) {
        entityManager.persist(role);
    }

    @Override
    public List<Role> getRoles() {
        return entityManager.createQuery("select r from Role r", Role.class).getResultList();
    }
}
