package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

    @Override
    public Role find(Long id) {
        try {
            return entityManager.createQuery(
                            "SELECT r FROM Role r WHERE r.id = :id", Role.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
