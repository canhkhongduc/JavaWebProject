/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Account;
import model.Attempt;
import model.Question;
import model.Test;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.hibernate.HibernateUtil;

/**
 *
 * @author Niles
 */
public class TestMasterManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestMasterManager.class);
    
    private final SessionFactory sessionFactory;
    
    public TestMasterManager() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }
    
    public boolean addTest(Test test) {
        boolean success = true;
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.save(test);
            session.getTransaction().commit();
        } catch (RuntimeException ex) {
            success = false;
            session.getTransaction().rollback();
            LOGGER.error(ex.toString());
        }
        return success;
    }
    
    public boolean updateTest(Test test) {
        boolean success = true;
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.update(test);
            session.getTransaction().commit();
        } catch (RuntimeException ex) {
            success = false;
            session.getTransaction().rollback();
            LOGGER.error(ex.toString());
        }
        return success;
    }
    
    public boolean deleteTest(Test test) {
        boolean success = true;
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.delete(test);
            session.getTransaction().commit();
        } catch (RuntimeException ex) {
            success = false;
            session.getTransaction().rollback();
            LOGGER.error(ex.toString());
        }
        return success;
    }
    
    public boolean addQuestion(Question question) {
        boolean success = true;
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.save(question);
            session.getTransaction().commit();
        } catch (RuntimeException ex) {
            success = false;
            session.getTransaction().rollback();
            LOGGER.error(ex.toString());
        }
        return success;
    }
    
    public boolean updateQuestion(Question question) {
        boolean success = true;
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.update(question);
            session.getTransaction().commit();
        } catch (RuntimeException ex) {
            success = false;
            session.getTransaction().rollback();
            LOGGER.error(ex.toString());
        }
        return success;
    }
    
    public boolean deleteQuestion(Question question) {
        boolean success = true;
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.delete(question);
            session.getTransaction().commit();
        } catch (RuntimeException ex) {
            success = false;
            session.getTransaction().rollback();
            LOGGER.error(ex.toString());
        }
        return success;
    }
    
    public boolean addStudentToPrivateTest(Attempt attempt) {
        boolean success = true;
        Session session = sessionFactory.getCurrentSession();
        
        try {
            session.beginTransaction();
            session.save(attempt);
            session.getTransaction().commit();
        } catch (RuntimeException ex) {
            success = false;
            session.getTransaction().rollback();
            LOGGER.error(ex.toString());
        }
        return success;
    }
    
    public boolean deleteStudentFromPrivateTest(Attempt attempt) {
        boolean success = true;
        Session session = sessionFactory.getCurrentSession();
        
        try {
            session.beginTransaction();
            session.delete(attempt);
            session.getTransaction().commit();
        } catch (RuntimeException ex) {
            success = false;
            session.getTransaction().rollback();
            LOGGER.error(ex.toString());
        }
        return success;
    }
    
    public boolean addQuestionToTest(Question question, Test test) {
        boolean success = true;
        Session session = sessionFactory.getCurrentSession();
        
        try {
            session.beginTransaction();
            test.getQuestions().add(question);
            session.save(test);
            session.getTransaction().commit();
        } catch (RuntimeException ex) {
            success = false;
            session.getTransaction().rollback();
            LOGGER.error(ex.toString());
        }
        return success;
    }
}
