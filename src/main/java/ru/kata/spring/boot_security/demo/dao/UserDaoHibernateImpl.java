package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class UserDaoHibernateImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public UserDaoHibernateImpl() {
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("select u from  User u ", User.class).getResultList();
    }

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public User getUser(Long id) {
        User user = entityManager.find(User.class, id);
        return user;
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void deleteUser(Long id) {
        entityManager.createQuery("delete from User where id=: id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public User findByName(String email) {
        return entityManager
                .createQuery("select u from User u where u.email = :email", User.class)
                .setParameter("email", email)
                .getSingleResult();
    }
}
