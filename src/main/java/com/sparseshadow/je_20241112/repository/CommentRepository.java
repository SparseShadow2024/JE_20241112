package com.sparseshadow.je_20241112.repository;

import com.sparseshadow.je_20241112.model.Comment;
import com.sparseshadow.je_20241112.util.HibernateUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import java.util.List;

@ApplicationScoped  
public class CommentRepository {

    public List<Comment> findByArticleId(Integer id) throws PersistenceException {
        EntityManager em = HibernateUtil.getEntityManager();
        List<Comment> comments = null;
        try {
            em.getTransaction().begin();  
            comments = em
                    .createQuery("SELECT c FROM Comment c WHERE c.articleId = :articleId", Comment.class)
                    .setParameter("articleId", id)
                    .getResultList();  
            em.getTransaction().commit();  
        } catch (PersistenceException e) {
            em.getTransaction().rollback();  
            throw new RuntimeException("", e);
        } finally {
            em.close();  
        }
        return comments;
    }

    public void create(Comment comment) throws PersistenceException {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();  
            em.persist(comment);  
            em.getTransaction().commit();  
        } catch (PersistenceException e) {
            em.getTransaction().rollback();  
            throw new RuntimeException("", e);
        } finally {
            em.close();  
        }
    }

    // 新增删除评论的方法
    public void deleteById(Integer id) throws PersistenceException {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Comment comment = em.find(Comment.class, id); // 根据主键查找评论
            if (comment != null) {
                em.remove(comment); // 删除评论
            } else {
                throw new RuntimeException("Comment not found with id: " + id);
            }
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error deleting comment", e);
        } finally {
            em.close();
        }
    }
}
