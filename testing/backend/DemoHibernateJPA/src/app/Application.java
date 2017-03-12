/*
 * Copyright 2017 Le Cao Nguyen
 */
package app;

import java.util.List;
import model.Account;
import model.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import util.hibernate.HibernateUtil;

/**
 *
 * @author Le Cao Nguyen
 */
public class Application implements Runnable {

    private SessionFactory sessionFactory;

    public Application() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void createRoles() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        Role adminRole = new Role("admin", "Administrator");
        Role testMasterRole = new Role("testmaster", "Test Master");
        Role studentRole = new Role("student", "Student");
        
        session.save(adminRole);
        session.save(testMasterRole);
        session.save(studentRole);
        
        session.getTransaction().commit();
    }
    
    public void createAccounts() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        Account acc1 = new Account();
        acc1.setUsername("admin");
        acc1.setPassword("admin");
        Account acc2 = new Account();
        acc2.setUsername("nguyen");
        acc2.setPassword("123456");
        Account acc3 = new Account();
        acc3.setUsername("john");
        acc3.setPassword("123456");
        
        session.save(acc1);
        session.save(acc2);
        session.save(acc3);

        session.getTransaction().commit();
    }
    
    public void associate() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        List<Role> roles = session.createCriteria(Role.class).list();
        List<Account> accounts = session.createCriteria(Account.class).list();
        
        accounts.get(0).getRoles().add(roles.get(0));
        accounts.get(0).getRoles().add(roles.get(1));
        accounts.get(1).getRoles().add(roles.get(1));
        accounts.get(1).getRoles().add(roles.get(2));
        accounts.get(2).getRoles().add(roles.get(2));

        session.getTransaction().commit();
    }

    private void removeRolesFromAccount() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Account acc = (Account) session.createCriteria(Account.class).add(Restrictions.eq("username", "nguyen")).uniqueResult();
        acc.getRoles().removeIf((role) -> role.getName().equals("testmaster"));
        session.update(acc);
        session.getTransaction().commit();
    }

    private void removeAccountsFromRole() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Role studentRole = (Role) session.get(Role.class, "student");
//        session.getTransaction().commit();
//        session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//        session.refresh(studentRole);
        studentRole.getAccounts().removeIf((account) -> account.getUsername().equals("john"));
        session.update(studentRole);
        session.getTransaction().commit();
    }

    private void deleteRole() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Role studentRole = (Role) session.get(Role.class, "student");
        session.delete(studentRole);
        session.getTransaction().commit();
    }

    private void deleteAccount() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Account acc = (Account) session.createCriteria(Account.class).add(Restrictions.eq("username", "nguyen")).uniqueResult();
        session.delete(acc);
        session.getTransaction().commit();
    }

    @Override
    public void run() {
        createRoles();
        createAccounts();
        associate();
//        removeRolesFromAccount();
//        removeAccountsFromRole();
//        deleteRole();
//        deleteAccount();
    }

    public static void main(String[] args) {
        new Application().run();
        System.exit(0);
    }

}
