/*
 * Copyright © 2017 Six Idiots Team
 */
package dao;

import java.util.List;
import model.Account;
import model.Course;
import model.Test;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import util.hibernate.transaction.TransactionPerformer;

/**
 *
 * @author nguyen
 */
public class TestManager extends TransactionPerformer {

    public List<Test> getAllTests() {
        return performTransaction((session) -> {
            Criteria criteria = session.createCriteria(Test.class);
            criteria.addOrder(Order.asc("id"));
            return criteria.list();
        });
    }

    public List<Test> getTests(Account owner) {
        return performTransaction((session) -> {
            Criteria criteria = session.createCriteria(Test.class);
            criteria.addOrder(Order.asc("id"));
            criteria.add(Restrictions.eq("owner", owner));
            return criteria.list();
        });
    }
    
    public List<Test> getTests(Course course) {
        return performTransaction((session) -> {
            Criteria criteria = session.createCriteria(Test.class);
            criteria.addOrder(Order.asc("id"));
            criteria.add(Restrictions.eq("course", course));
            return criteria.list();
        });
    }
    
    public List<Test> getAccessibleTests(Account account) {
        return performTransaction((session) -> {
            Criteria criteria = session.createCriteria(Test.class);
            criteria.addOrder(Order.asc("id"));
            criteria.add(Restrictions.or(Restrictions.eq("owner", account), Restrictions.eq("restricted", false)));
            List<Test> tests = criteria.list();
            for (Test test : tests) {
                Hibernate.initialize(test.getOwner());
            }
            return tests;
        });        
    }

    public Test getTest(Long id) {
        return performTransaction((session) -> {
            return (Test) session.get(Test.class, id);
        });
    }

    public boolean addTest(Test test) {
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
        return performTransaction((session) -> {
            session.delete(test);
        });
    }
}
