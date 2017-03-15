/*
 * Copyright © 2017 Six Idiots Team
 */
package util.hibernate.transaction;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.LoggerFactory;
import util.hibernate.HibernateUtil;

/**
 *
 * @author Le Cao Nguyen
 */
public class TransactionPerformer {
    
    protected final SessionFactory sessionFactory;

    public TransactionPerformer() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }
    
    public <T> T performTransaction(QueryTransaction<T> transaction) {
        T result = null;
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            result = transaction.apply(session);
            session.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (tx != null) tx.rollback();
            LoggerFactory.getLogger(this.getClass()).error(null, ex);
        }
        return result;
    }
    
    public boolean performTransaction(UpdateTransaction transaction) {
        boolean success = true;
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            transaction.apply(session);
            session.getTransaction().commit();
        } catch (RuntimeException ex) {
            success = false;
            if (tx != null) tx.rollback();
            LoggerFactory.getLogger(this.getClass()).error(null, ex);
        }
        return success;
    }
}
