/*
 * Copyright 2017 Le Cao Nguyen
 */
package tester;

import java.sql.Date;
import model.Account;
import model.AccountProfile;
import model.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import util.hibernate.HibernateUtil;

/**
 *
 * @author Le Cao Nguyen
 */
public class AccountTester implements Runnable {

    private final SessionFactory sessionFactory;

    public AccountTester() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    private void removeRolesFromAccount() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Account acc = (Account) session.createCriteria(Account.class).add(Restrictions.eq("username", "nguyen")).uniqueResult();
        acc.removeRole((Role) session.get(Role.class, "testmaster"));
        session.update(acc);
        session.getTransaction().commit();
    }

    private void deleteAccount() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Account acc = (Account) session.createCriteria(Account.class).add(Restrictions.eq("username", "john")).uniqueResult();
        session.delete(acc);
        session.getTransaction().commit();
    }

    private void initData() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Role adminRole = new Role("admin", "Administrator");
        Role testMasterRole = new Role("testmaster", "Test Master");
        Role studentRole = new Role("student", "Student");

        Account acc1 = new Account();
        acc1.setUsername("admin");
        acc1.setPassword("admin");
        acc1.setProfile(new AccountProfile("Bố già", Boolean.TRUE, Date.valueOf("1993-12-26"), null));
        Account acc2 = new Account();
        acc2.setUsername("nguyen");
        acc2.setPassword("123456");
        acc2.setProfile(new AccountProfile("Lê Cao Nguyên", Boolean.TRUE, Date.valueOf("1993-12-26"), "nguyenlc1993@gmail.com"));
        Account acc3 = new Account();
        acc3.setUsername("john");
        acc3.setPassword("123456");
        acc3.setProfile(new AccountProfile());

        acc1.addRole(adminRole);
        acc1.addRole(testMasterRole);
        acc2.addRole(testMasterRole);
        acc2.addRole(studentRole);
        acc3.addRole(studentRole);

        session.save(acc1);
        session.save(acc2);
        session.save(acc3);

        session.getTransaction().commit();
    }

    @Override
    public void run() {
        initData();
        removeRolesFromAccount();
        deleteAccount();
    }
}
