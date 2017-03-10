/*
 * Copyright © 2017 Six Idiots Team
 */
package dao;

import util.hibernate.HibernateUtil;
import java.util.List;
import model.Account;
import model.Test;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author nguyen
 */
public class TestManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestManager.class);

    private final SessionFactory sessionFactory;

    public TestManager() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<Test> getAllTests() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Test.class);
        criteria.addOrder(Order.asc("id"));
        List<Test> tests = criteria.list();
        session.getTransaction().commit();
        return tests;
    }

    public List<Test> getTestsByAccount(Account account) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Test.class);
        criteria.addOrder(Order.asc("id"));
        criteria.add(Restrictions.eq("owner", account));
        List<Test> tests = criteria.list();
        session.getTransaction().commit();
        return tests;
    }

    public Test getTest(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Test test = (Test) session.get(Test.class, id);
        session.getTransaction().commit();
        return test;
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
}
