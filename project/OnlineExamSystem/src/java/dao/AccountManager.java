/*
 * Copyright © 2017 Six Idiots Team
 */
package dao;

import java.util.List;
import model.Account;
import model.Role;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import util.hibernate.transaction.TransactionPerformer;

/**
 *
 * @author nguyen
 */
public class AccountManager extends TransactionPerformer {

    public List<Account> getAllAccounts() {
        return performTransaction((session) -> {
            Criteria criteria = session.createCriteria(Account.class);
            criteria.addOrder(Order.asc("username"));
            return criteria.list();
        });
    }

    public Account getAccount(String username) {
        return performTransaction((session) -> {
            return (Account) session.get(Account.class, username);
        });
    }

    public boolean hasAccount(String username) {
        return performTransaction((session) -> {
            Criteria criteria = session.createCriteria(Account.class);
            criteria.add(Restrictions.eq("username", username));
            criteria.setProjection(Projections.rowCount());
            long count = (long) criteria.uniqueResult();
            return count > 0;
        });
    }

    public boolean saveAccount(Account account) {
        return performTransaction((session) -> {
            session.save(account);
        });
    }

    public boolean updateAccount(Account account) {
        return performTransaction((session) -> {
            session.update(account);
        });
    }

    public boolean deleteAccount(Account account) {
        return performTransaction((session) -> {
            session.delete(account);
        });
    }
    
    public List<Account> getAccountsByRole(String role) {
        return performTransaction((session) -> {
            Criteria criteria = session.createCriteria(Account.class);
            criteria.createAlias("roles", "rolesAlias");
            criteria.add(Restrictions.eq("rolesAlias.name", role));
            criteria.addOrder(Order.asc("username"));
            return criteria.list();
        });
    }
}
