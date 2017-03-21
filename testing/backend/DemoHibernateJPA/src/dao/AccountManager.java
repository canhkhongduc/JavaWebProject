/*
 * Copyright © 2017 Six Idiots Team
 */
package dao;

import java.util.List;
import model.Account;
import model.Attempt;
import model.Question;
import model.Role;
import model.Test;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
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

    public List<Account> getAccountsByRole(Role role) {
        return performTransaction((session) -> {
            Criteria criteria = session.createCriteria(Account.class);
            criteria.createAlias("roles", "rolesAlias");
            criteria.add(Restrictions.eq("rolesAlias.name", role.getName()));
            criteria.addOrder(Order.asc("username"));
            return criteria.list();
        });
    }

    public boolean hasAccount(String username) {
        return getAccount(username) != null;
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
        if (!removeAllReferences(account)) {
            throw new HibernateException("Could not remove all references to account.");
        }
        return performTransaction((session) -> {
            session.delete(account);
        });
    }

    /**
     * Reference removal for account:<br>
     * - All questions owned by this account -> set owner = NULL<br>
     * - All tests owned by this account -> set owner = NULL<br>
     * - If accounts is an examinee of a test -> remove account from
     * test.examinees<br>
     * - All attempts submitted by this account -> set examinee = NULL<br>
     */
    public boolean removeAllReferences(Account account) {
        return performTransaction((session) -> {
            List<Question> ownedQuestions = session.createCriteria(Question.class)
                    .add(Restrictions.eq("owner", account)).list();
            ownedQuestions.forEach((question) -> {
                question.setOwner(null);
                session.update(question);
            });
            List<Test> ownedTests = session.createCriteria(Test.class)
                    .add(Restrictions.eq("owner", account)).list();
            ownedTests.stream().forEach((test) -> {
                test.setOwner(null);
                session.update(test);
            });
            List<Test> assignedTests = session.createCriteria(Test.class).createAlias("examinees", "examineesAlias")
                    .add(Restrictions.eq("examineesAlias.username", account.getUsername())).list();
            assignedTests.stream().forEach((test) -> {
                test.removeExaminee(account);
                session.update(test);
            });
            List<Attempt> attempts = session.createCriteria(Attempt.class)
                    .add(Restrictions.eq("examinee", account)).list();
            attempts.forEach((attempt) -> {
                attempt.setExaminee(null);
                session.update(attempt);
            });
        });
    }
}
