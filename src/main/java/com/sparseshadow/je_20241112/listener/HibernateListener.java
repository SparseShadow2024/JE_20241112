package com.sparseshadow.je_20241112.listener;

import com.sparseshadow.je_20241112.util.HibernateUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.persistence.PersistenceException;

import javax.servlet.annotation.WebListener;

@WebListener
public class HibernateListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            HibernateUtil.init();   
        } catch (PersistenceException ex) {
            System.err.println("Database connection failed. Server will shut down.");
            shutdownServer();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (HibernateUtil.getEntityManagerFactory() != null && HibernateUtil.getEntityManagerFactory().isOpen()) {
            HibernateUtil.getEntityManagerFactory().close();
        }
    }

    private void shutdownServer() {
        System.exit(1);
    }
}
