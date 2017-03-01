package dao;

import util.hibernate.HibernateUtil;
import java.util.List;
import model.Account;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author nguyen
 */
public class AccountManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountManager.class);
    
    private final SessionFactory sessionFactory;

    public AccountManager() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<Account> getAllAccounts() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Account.class);
        criteria.addOrder(Order.asc("username"));
        List<Account> accounts = criteria.list();
        session.getTransaction().commit();
        return accounts;
    }
    
    public Account getAccount(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Account account = (Account) session.get(Account.class, id);
        session.getTransaction().commit();
        return account;
    }

    public Account getAccount(String username) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Account.class);
        criteria.add(Restrictions.eq("username", username));
        criteria.setMaxResults(1);
        Account account = (Account) criteria.uniqueResult();
        session.getTransaction().commit();
        return account;
    }
    
    public boolean hasAccount(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Account.class);
        criteria.add(Restrictions.eq("id", id));
        criteria.setProjection(Projections.rowCount());
        int count = (int) criteria.uniqueResult();
        session.getTransaction().commit();
        return count > 0;
    }
    
    public boolean hasAccount(String username) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Account.class);
        criteria.add(Restrictions.eq("username", username));
        criteria.setProjection(Projections.rowCount());
        int count = (int) criteria.uniqueResult();
        session.getTransaction().commit();
        return count > 0;
    }
    
    public boolean addAccount(Account account) {
        boolean success = true;
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.save(account);
            session.getTransaction().commit();
        } catch (RuntimeException ex) {
            success = false;
            session.getTransaction().rollback();
            LOGGER.error(ex.toString());
        }
        return success;
    }
    
    public boolean updateAccount(Account account) {
        boolean success = true;
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.update(account);
            session.getTransaction().commit();
        } catch (RuntimeException ex) {
            success = false;
            session.getTransaction().rollback();
            LOGGER.error(ex.toString());
        }
        return success;
    }
    
    public boolean deleteAccount(Account account) {
        boolean success = true;
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.delete(account);
            session.getTransaction().commit();
        } catch (RuntimeException ex) {
            success = false;
            session.getTransaction().rollback();
            LOGGER.error(ex.toString());
        }
        return success;
    }
}
