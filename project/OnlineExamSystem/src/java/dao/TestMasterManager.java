/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Account;
import model.Attempt;
import model.Choice;
import model.Course;
import model.Group;
import model.Permission;
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
            session.saveOrUpdate(test);
            session.getTransaction().commit();
        } catch (RuntimeException ex) {
            success = false;
            session.getTransaction().rollback();
            LOGGER.error(ex.toString());
        }
        return success;
    }
    
    public boolean removeQuestionFromTest(Question question, Test test) {
        boolean success = true;
        Session session = sessionFactory.getCurrentSession();
        
        try {
            session.beginTransaction();
            test.getQuestions().remove(question);
            session.saveOrUpdate(test);
            session.getTransaction().commit();
        } catch (RuntimeException ex) {
            success = false;
            session.getTransaction().rollback();
            LOGGER.error(ex.toString());
        }
        return success;
    }
    
    public boolean addGroup(Group group) {
        boolean success = true;
        Session session = sessionFactory.getCurrentSession();
        
        try {
            session.beginTransaction();
            session.save(group);
            session.getTransaction().commit();
        } catch (RuntimeException ex) {
            success = false;
            session.getTransaction().rollback();
            LOGGER.error(ex.toString());
        }
        return success;
    }
    
    public boolean addCourse(Course course) {
        boolean success = true;
        Session session = sessionFactory.getCurrentSession();
        
        try {
            session.beginTransaction();
            session.save(course);
            session.getTransaction().commit();
        } catch (RuntimeException ex) {
            success = false;
            session.getTransaction().rollback();
            LOGGER.error(ex.toString());
        }
        return success;
    }
    
    public boolean addChoiceToQuestion(Question question, Choice choice) {
        boolean success = true;
        Session session = sessionFactory.getCurrentSession();
        
        try {
            session.beginTransaction();
            question.getChoices().add(choice);
            session.saveOrUpdate(question);
            session.getTransaction().commit();
        } catch (RuntimeException ex) {
            success = false;
            session.getTransaction().rollback();
            LOGGER.error(ex.toString());
        }
        return success;
    }
    
    public boolean removeChoiceFromQuestion(Question question, Choice choice) {
        boolean success = true;
        Session session = sessionFactory.getCurrentSession();
        
        try {
            session.beginTransaction();
            question.getChoices().remove(choice);
            session.saveOrUpdate(question);
            session.getTransaction().commit();
        } catch (RuntimeException ex) {
            success = false;
            session.getTransaction().rollback();
            LOGGER.error(ex.toString());
        }
        return success;
    }
   
    public boolean addPermissionForGroup(Permission permission, Group group) {
        boolean success = true;
        Session session = sessionFactory.getCurrentSession();
        
        try {
            session.beginTransaction();
            group.getPermissions().add(permission);
            session.saveOrUpdate(group);
            session.getTransaction().commit();
        } catch (RuntimeException ex) {
            success = false;
            session.getTransaction().rollback();
            LOGGER.error(ex.toString());
        }
        return success;
    }
    
    public boolean removePermissionFromGroup(Permission permission, Group group) {
        boolean success = true;
        Session session = sessionFactory.getCurrentSession();
        
        try {
            session.beginTransaction();
            group.getPermissions().remove(permission);
            session.saveOrUpdate(group);
            session.getTransaction().commit();
        } catch (RuntimeException ex) {
            success = false;
            session.getTransaction().rollback();
            LOGGER.error(ex.toString());
        }
        return success;
    }
}
