/*
 * Copyright 2017 Le Cao Nguyen
 */
package tester;

import dao.AccountManager;
import dao.AttemptManager;
import dao.TestManager;
import java.util.List;
import model.Account;
import model.Attempt;
import model.Test;
import util.hibernate.transaction.TransactionPerformer;

/**
 *
 * @author Le Cao Nguyen
 */
public class AttemptTester extends TransactionPerformer implements Runnable {

    @Override
    public void run() {
        TestManager testManager = new TestManager();
        Test test = testManager.getTest(1L);
        AttemptManager attemptManager = new AttemptManager();
        List<Attempt> attemptsWithOnlyLatest = attemptManager.getAttempts(test, true);
        attemptsWithOnlyLatest.stream().forEach((attempt) -> {
            System.out.printf("Student %s - Attempt %d\n", attempt.getExaminee().getUsername(), attempt.getId());
        });
        
        AccountManager accountManager = new AccountManager();
        Account examinee = accountManager.getAccount("minhnqse04781@fpt.edu.vn");
        Attempt attempt = attemptManager.getOnlyLatestAttempt(test, examinee);
        if (attempt == null) {
            System.out.println("Null");
        } else {
            System.out.printf("Student %s - Attempt %d\n", attempt.getExaminee().getUsername(), attempt.getId());
        }
    }

}
