/*
 * Copyright © 2017 Six Idiots Team
 */
package dao;

import java.util.Date;
import java.util.List;
import model.Account;
import model.Course;
import model.Test;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import util.hibernate.transaction.TransactionPerformer;

/**
 *
 * @author nguyen
 */
public class TestManager extends TransactionPerformer {

    public static void initializeProperties(Test test) {
        Hibernate.initialize(test.getQuestions());
        test.getQuestions().forEach((question) -> {
            QuestionManager.initializeProperties(question);
        });
        Hibernate.initialize(test.getExaminees());
    }

    public List<Test> getAllTests() {
        return getAllTests(false);
    }

    public List<Test> getAllTests(boolean fetch) {
        return performTransaction((session) -> {
            Criteria criteria = session.createCriteria(Test.class);
            criteria.addOrder(Order.asc("id"));
            List<Test> tests = criteria.list();
            if (fetch) {
                tests.forEach((test) -> {
                    initializeProperties(test);
                });
            }
            return tests;
        });
    }

    public List<Test> getTestsByOwner(Account owner) {
        return getTestsByOwner(owner, false);
    }

    public List<Test> getTestsByOwner(Account owner, boolean fetch) {
        return performTransaction((session) -> {
            Criteria criteria = session.createCriteria(Test.class);
            criteria.addOrder(Order.asc("id"));
            criteria.add(Restrictions.eq("owner", owner));
            List<Test> tests = criteria.list();
            if (fetch) {
                tests.forEach((test) -> {
                    initializeProperties(test);
                });
            }
            return tests;
        });
    }

    public List<Test> getTestsByCourse(Course course) {
        return getTestsByCourse(course, false);
    }

    public List<Test> getTestsByCourse(Course course, boolean fetch) {
        return performTransaction((session) -> {
            Criteria criteria = session.createCriteria(Test.class);
            criteria.addOrder(Order.asc("id"));
            criteria.add(Restrictions.eq("course", course));
            List<Test> tests = criteria.list();
            if (fetch) {
                tests.forEach((test) -> {
                    initializeProperties(test);
                });
            }
            return tests;
        });
    }

    public List<Test> getAccessibleTests(Account examinee) {
        return getAccessibleTests(examinee, false);
    }

    public List<Test> getAccessibleTests(Account examinee, boolean fetch) {
        return performTransaction((session) -> {
            Criteria criteria = session.createCriteria(Test.class);
            criteria.addOrder(Order.asc("id"));
            criteria.add(Restrictions.or(Restrictions.eq("owner", examinee), Restrictions.eq("restricted", false)));
            List<Test> tests = criteria.list();
            if (fetch) {
                tests.forEach((test) -> {
                    initializeProperties(test);
                });
            }
            return tests;
        });
    }

    public List<Test> getTestsByDateRange(Date timeFrom, Date timeTo) {
        return getTestsByDateRange(timeFrom, timeTo, false);
    }

    public List<Test> getTestsByDateRange(Date timeFrom, Date timeTo, boolean fetch) {
        return performTransaction((session) -> {
            Criteria criteria = session.createCriteria(Test.class);
            criteria.addOrder(Order.asc("id"));
            criteria.add(Restrictions.between("joinStartTime", timeFrom, timeTo));
            List<Test> tests = criteria.list();
            if (fetch) {
                tests.forEach((test) -> {
                    initializeProperties(test);
                });
            }
            return tests;
        });
    }

    public Test getTest(Long id) {
        return getTest(id, false);
    }

    public Test getTest(Long id, boolean fetch) {
        return performTransaction((session) -> {
            Test test = (Test) session.get(Test.class, id);
            if (fetch) {
                initializeProperties(test);
            }
            return test;
        });
    }

    public boolean saveTest(Test test) {
        return performTransaction((session) -> {
            session.save(test);
        });
    }

    public boolean updateTest(Test test) {
        return performTransaction((session) -> {
            session.update(test);
        });
    }

    public boolean deleteTest(Test test) {
        if (!removeAllReferences(test)) {
            throw new HibernateException("Could not remove all references to test.");
        }
        return performTransaction((session) -> {
            session.delete(test);
        });
    }

    /**
     * Reference removal for test: None<br>
     * - When delete a test, all attempts belong to that test will also be
     * deleted due to cascade.<br>
     */
    public boolean removeAllReferences(Test test) {
        return true;
    }
}
