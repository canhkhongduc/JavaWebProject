/*
 * Copyright 2017 Le Cao Nguyen
 */
package tester;

import java.sql.Date;
import model.Account;
import model.AccountProfile;
import model.Role;
import util.hibernate.transaction.TransactionPerformer;

/**
 *
 * @author Le Cao Nguyen
 */
public class AccountTester extends TransactionPerformer implements Runnable {

    private void removeRolesFromAccount() {
        performTransaction((session) -> {
            Account acc = (Account) session.get(Account.class, "nguyen");
            acc.removeRole((Role) session.get(Role.class, "testmaster"));
            session.update(acc);
        });
    }
    
    private void removeRolesFromAccount2() {
        performTransaction((session) -> {
            Account acc = (Account) session.get(Account.class, "john");
            acc.getRoles().clear();
            session.update(acc);
        });
    }

    private void deleteAccount() {
        performTransaction((session) -> {
            Account acc = (Account) session.get(Account.class, "john");
            session.delete(acc);
        });
    }

    private void initData() {
        performTransaction((session) -> {
            Account acc = (Account) session.get(Account.class, "john");
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
            
            acc1.addRole(adminRole);
            acc1.addRole(testMasterRole);
            acc2.addRole(testMasterRole);
            acc2.addRole(studentRole);
            acc3.addRole(studentRole);

            session.save(acc1);
            session.save(acc2);
            session.save(acc3);
        });
    }

    @Override
    public void run() {
        initData();
        removeRolesFromAccount2();
        deleteAccount();
    }
}
