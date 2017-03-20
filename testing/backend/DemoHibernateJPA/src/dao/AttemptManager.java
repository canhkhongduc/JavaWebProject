/*
 * Copyright © 2017 Six Idiots Team
 */
package dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
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
        return getAttempts(test, false);
    }

    public List<Attempt> getAttempts(Test test, boolean onlyLatestByExaminee) {
        return performTransaction((session) -> {
            Criteria criteria = session.createCriteria(Attempt.class);
            criteria.addOrder(Order.asc("id"));
            criteria.add(Restrictions.eq("test", test));
            List<Attempt> attempts = criteria.list();
            if (onlyLatestByExaminee) {
                Map<Account, Optional<Attempt>> latestAttemptOptionals = attempts.stream().collect(
                        Collectors.groupingBy(
                                (attempt) -> attempt.getExaminee(),
                                Collectors.maxBy((attempt1, attempt2) -> Long.compare(attempt1.getId(), attempt2.getId()))
                        )
                );
                List<Attempt> latestAttempts = latestAttemptOptionals.values().stream()
                        .map((optional) -> optional.get())
                        .sorted((attempt1, attempt2) -> Long.compare(attempt1.getId(), attempt2.getId()))
                        .collect(Collectors.toList());
                return latestAttempts;
            } else {
                return attempts;
            }
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
    
    public Attempt getOnlyLatestAttempt(Test test, Account examinee) {
        return performTransaction((session) -> {
            Criteria criteria = session.createCriteria(Attempt.class);
            criteria.addOrder(Order.asc("id"));
            criteria.add(Restrictions.eq("test", test));
            criteria.add(Restrictions.eq("examinee", examinee));
            List<Attempt> attempts = criteria.list();
            Optional<Attempt> optional = attempts.stream().collect(Collectors.maxBy((attempt1, attempt2) -> Long.compare(attempt1.getId(), attempt2.getId())));
            return optional.orElse(null);
        });
    }
}
