/*
 * Copyright © 2017 Six Idiots Team
 */
package dao;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import model.Account;
import model.Attempt;
import model.Test;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import util.hibernate.transaction.TransactionPerformer;

/**
 *
 * @author Niles
 */
public class AttemptManager extends TransactionPerformer {

    public Attempt getAttempt(Long id) {
        return performTransaction((session) -> {
            return (Attempt) session.get(Attempt.class, id);
        });
    }

    public List<Attempt> getAttempts(Test test) {
        return performTransaction((session) -> {
            Criteria criteria = session.createCriteria(Attempt.class);
            criteria.addOrder(Order.asc("id"));
            criteria.add(Restrictions.eq("test", test));
            return criteria.list();
        });
    }

    public List<Attempt> getAttempts(Test test, Account examinee) {
        return performTransaction((session) -> {
            Criteria criteria = session.createCriteria(Attempt.class);
            criteria.addOrder(Order.asc("id"));
            criteria.add(Restrictions.eq("test", test));
            criteria.add(Restrictions.eq("examinee", examinee));
            return criteria.list();
        });
    }
}
