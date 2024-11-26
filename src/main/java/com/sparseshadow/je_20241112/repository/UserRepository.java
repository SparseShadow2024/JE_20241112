package com.sparseshadow.je_20241112.repository;

import com.sparseshadow.je_20241112.model.User;
import com.sparseshadow.je_20241112.util.HibernateUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;


import java.util.List;

@ApplicationScoped  
public class UserRepository {

    public List<User> findAll() throws PersistenceException {
        EntityManager em = HibernateUtil.getEntityManager();
        List<User> users = null;
        try {
            em.getTransaction().begin();  
            users = em
                    .createQuery("SELECT u FROM User u", User.class)
                    .getResultList();  
            em.getTransaction().commit();  
        } catch (PersistenceException e) {
            em.getTransaction().rollback();  
            throw new RuntimeException("", e);
        } finally {
            em.close();  
        }
        return users;
    }


    public User findByID(Integer id) throws PersistenceException {
        EntityManager em = HibernateUtil.getEntityManager();
        User user = null;
        try {
            em.getTransaction().begin();  
            user = em
                    .createQuery("SELECT u FROM User u WHERE u.id = :id", User.class)
                    .setParameter("id", id)
                    .getSingleResult();  
            em.getTransaction().commit();  
        } catch (PersistenceException e) {
            em.getTransaction().rollback();  
            throw new RuntimeException("user_not_found", e);
        } finally {
            em.close();  
        }
        return user;
    }

    public User findByUsername(String username) throws PersistenceException {
        EntityManager em = HibernateUtil.getEntityManager();
        User user = null;
        try {
            em.getTransaction().begin();  
            user = em
                    .createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();  
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();  
            throw new RuntimeException("user_not_found", e);
        } finally {
            em.close();  
        }
        return user;
    }

    public void create(User user) throws PersistenceException {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();  
            em.persist(user);  
            em.getTransaction().commit();  
        } catch (PersistenceException e) {
            em.getTransaction().rollback();  
            throw new RuntimeException("user_already_exists", e);
        } finally {
            em.close();  
        }
    }
}