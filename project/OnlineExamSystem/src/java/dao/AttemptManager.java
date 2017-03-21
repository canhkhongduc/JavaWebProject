/*
 * Copyright © 2017 Six Idiots Team
 */
package dao;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import java.util.List;
import model.Account;
import model.Attempt;
import model.Test;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import util.hibernate.transaction.TransactionPerformer;

/**
 *
 * @author Niles
 */
public class AttemptManager extends TransactionPerformer {

    public static void initializeProperties(Attempt attempt) {
        Hibernate.initialize(attempt.getChoices());
    }

    public Attempt getAttempt(Long id) {
        return getAttempt(id, false);
    }

    public Attempt getAttempt(Long id, boolean fetch) {
        return performTransaction((session) -> {
            Attempt attempt = (Attempt) session.get(Attempt.class, id);
            if (fetch) {
                initializeProperties(attempt);
            }
            return attempt;
        });
    }

    public List<Attempt> getAllAttempts() {
        return getAllAttempts(false);
    }
    
    public List<Attempt> getAllAttempts(boolean fetch) {
        return performTransaction((session) -> {
            Criteria criteria = session.createCriteria(Attempt.class);
            criteria.addOrder(Order.asc("id"));
            List<Attempt> attempts = criteria.list();
            if (fetch) {
                attempts.forEach((attempt) -> {
                    initializeProperties(attempt);
                });
            }
            return attempts;
        });
    }

    public List<Attempt> getAttemptsByTest(Test test) {
        return getAttemptsByTest(test, false);
    }

    public List<Attempt> getAttemptsByTest(Test test, boolean fetch) {
        return performTransaction((session) -> {
            Criteria criteria = session.createCriteria(Attempt.class);
            criteria.addOrder(Order.asc("id"));
            criteria.add(Restrictions.eq("test", test));
            List<Attempt> attempts = criteria.list();
            if (fetch) {
                attempts.forEach((attempt) -> {
                    initializeProperties(attempt);
                });
            }
            return attempts;
        });
    }

    public List<Attempt> filterLatestAttemptOfExaminee(List<Attempt> attempts) {
        attempts.removeIf((attempt) -> attempt.getExaminee() == null);
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
    }

    public List<Attempt> getAttemptsByTestAndExaminee(Test test, Account examinee) {
        return getAttemptsByTestAndExaminee(test, examinee, false);
    }
    
    public List<Attempt> getAttemptsByTestAndExaminee(Test test, Account examinee, boolean fetch) {
        return performTransaction((session) -> {
            Criteria criteria = session.createCriteria(Attempt.class);
            criteria.addOrder(Order.asc("id"));
            criteria.add(Restrictions.eq("test", test));
            criteria.add(Restrictions.eq("examinee", examinee));
            List<Attempt> attempts = criteria.list();
            if (fetch) {
                attempts.forEach((attempt) -> {
                    initializeProperties(attempt);
                });
            }
            return attempts;
        });
    }

    public boolean saveAttempt(Attempt attempt) {
        return performTransaction((session) -> {
            session.save(attempt);
        });
    }
    
    public boolean updateAttempt(Attempt attempt) {
        return performTransaction((session) -> {
            session.update(attempt);
        });
    }
    
    public boolean deleteAttempt(Attempt attempt) {
        if (!removeAllReferences(attempt)) {
            throw new HibernateException("Could not remove all references to attempt.");
        }
        return performTransaction((session) -> {
            session.delete(attempt);
        });
    }
    
    /**
     * Reference removal for test: None<br>
     */
    public boolean removeAllReferences(Attempt attempt) {
        return true;
    }
}
