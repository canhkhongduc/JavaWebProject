/*
 * Copyright 2017 Le Cao Nguyen
 */
package tester;

import dao.AccountManager;
import dao.RoleManager;
import java.sql.Date;
import java.util.List;
import model.Account;
import model.Role;
import util.hibernate.transaction.TransactionPerformer;

/**
 *
 * @author Le Cao Nguyen
 */
public class AccountTester extends TransactionPerformer implements Runnable {
    private final AccountManager accountManager = new AccountManager();
    private final RoleManager roleManager = new RoleManager();

    public boolean createAccounts() {
        boolean success = true;
        
        Account admin = new Account("admin", "admin");
        Account testmaster1 = new Account("testmaster1", "testmaster1");
        Account testmaster2 = new Account("testmaster2", "testmaster2");
        Account student1 = new Account("student1", "student1");
        Account student2 = new Account("student2", "student2");
        Account student3 = new Account("student3", "student3");
        
        Role adminRole = roleManager.getRole("admin");
        Role testmasterRole = roleManager.getRole("testmaster");
        Role studentRole = roleManager.getRole("student");
        
        admin.setOnlyOneRole(adminRole);
        testmaster1.setOnlyOneRole(testmasterRole);
        testmaster2.setOnlyOneRole(testmasterRole);
        student1.setOnlyOneRole(studentRole);
        student2.setOnlyOneRole(studentRole);
        student3.setOnlyOneRole(studentRole);
        
        success &= accountManager.saveAccount(admin);
        success &= accountManager.saveAccount(testmaster1);
        success &= accountManager.saveAccount(testmaster2);
        success &= accountManager.saveAccount(student1);
        success &= accountManager.saveAccount(student2);
        success &= accountManager.saveAccount(student3);
        
        return success;
    }
    
    public boolean updateAccountRoles() {
        boolean success = true;
        
        Account testmaster1 = accountManager.getAccount("testmaster1");
        Account student1 = accountManager.getAccount("student1");

        Role testmasterRole = roleManager.getRole("testmaster");
        Role studentRole = roleManager.getRole("student");
        
        testmaster1.setOnlyOneRole(studentRole);
        student1.setOnlyOneRole(testmasterRole);
        
        success &= accountManager.updateAccount(testmaster1);
        success &= accountManager.updateAccount(student1);

        testmaster1.setOnlyOneRole(testmasterRole);
        student1.setOnlyOneRole(studentRole);
        
        success &= accountManager.updateAccount(testmaster1);
        success &= accountManager.updateAccount(student1);

        return success;
    }
    
    public boolean updateAccountProfile() {
        boolean success = true;
        
        Account student1 = accountManager.getAccount("student1");
        
        student1.getProfile().setFullName("Student 1");
        student1.getProfile().setGender(true);
        student1.getProfile().setEmail("student1@abc.com");
        student1.getProfile().setBirthdate(Date.valueOf("2000-01-01"));
        success &= accountManager.updateAccount(student1);
        
        return success;
    }
    
    public boolean deleteAccounts() {
        boolean success = true;
        Account student1 = accountManager.getAccount("student1");
        success &= accountManager.deleteAccount(student1);
        List<Account> accounts = accountManager.getAllAccounts();
        for (Account account : accounts) {
            success &= accountManager.deleteAccount(account);
        }
        return success;
    }

    @Override
    public void run() {
        System.out.println("Creating accounts... " + (createAccounts() ? "Success!" : "Failed!"));
        System.out.println("Updating account roles... " + (updateAccountRoles() ? "Success!" : "Failed!"));
        System.out.println("Updating account profile... " + (updateAccountProfile() ? "Success!" : "Failed!"));
        System.out.println("Deleting accounts... " + (deleteAccounts() ? "Success!" : "Failed!"));
    }
}
