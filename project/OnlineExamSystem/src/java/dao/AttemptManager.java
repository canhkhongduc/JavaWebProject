/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Attempt;
import model.Test;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.hibernate.HibernateUtil;

/**
 *
 * @author Niles
 */
public class AttemptManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestManager.class);
    
    private final SessionFactory sessionFactory;

    public AttemptManager() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }
    
    public List<Attempt> getAttempts(Test test) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Attempt.class);
        criteria.addOrder(Order.asc("id"));
        criteria.add(Restrictions.eq("test", test));
        List<Attempt> attempts = criteria.list();
        for (Attempt attempt : attempts) {
            Hibernate.initialize(attempt.getExaminee());
        }
 
        session.getTransaction().commit();
        return attempts;
    }
}
