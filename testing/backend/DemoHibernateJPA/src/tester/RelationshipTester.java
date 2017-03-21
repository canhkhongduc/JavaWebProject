/*
 * Copyright 2017 Le Cao Nguyen
 */
package tester;

import dao.AccountManager;
import dao.AttemptManager;
import dao.CourseManager;
import dao.QuestionManager;
import dao.RoleManager;
import dao.TestManager;
import model.Account;
import model.Attempt;
import model.Course;
import model.Question;
import model.Role;
import model.Test;
import util.hibernate.transaction.TransactionPerformer;

/**
 *
 * @author Le Cao Nguyen
 */
public class RelationshipTester extends TransactionPerformer implements Runnable {

    private final QuestionManager questionManager = new QuestionManager();
    private final TestManager testManager = new TestManager();
    private final CourseManager courseManager = new CourseManager();
    private final AccountManager accountManager = new AccountManager();
    private final AttemptManager attemptManager = new AttemptManager();
    private final RoleManager roleManager = new RoleManager();

    private void printResult(String action, boolean result) {
        System.out.printf("%s ... %s\n", action, result ? "Success!" : "Failed!");
    }

    @Override
    public void run() {
        printResult("Delete a role", deleteRole());
        printResult("Delete 2 accounts", deleteAccounts());
        printResult("Delete a course", deleteCourse());
        printResult("Delete a question", deleteQuestion());
        printResult("Delete a test", deleteTest());
        printResult("Delete an attempt", deleteAttempt());
    }

    public boolean deleteRole() {
        boolean success = true;
        Role adminRole = roleManager.getRole("admin");
        success &= roleManager.deleteRole(adminRole);
        return success;
    }

    public boolean deleteAccounts() {
        boolean success = true;
        Account testmaster1 = accountManager.getAccount("testmaster1");
        Account student1 = accountManager.getAccount("student1");
        success &= accountManager.deleteAccount(testmaster1);
        success &= accountManager.deleteAccount(student1);
        return success;
    }

    public boolean deleteCourse() {
        boolean success = true;
        Course c01 = courseManager.getCourse("C01");
        success &= courseManager.deleteCourse(c01);
        return success;
    }

    public boolean deleteQuestion() {
        boolean success = true;
        Course c02 = courseManager.getCourse("C02");
        Question question = questionManager.getQuestionsByCourse(c02).get(0);
        success &= questionManager.deleteQuestion(question);
        return success;
    }

    public boolean deleteTest() {
        boolean success = true;
        Test test2 = testManager.getAllTests().get(1);
        success &= testManager.deleteTest(test2);
        return success;
    }

    public boolean deleteAttempt() {
        boolean success = true;
        Test test1 = testManager.getAllTests().get(0);
        Attempt attempt = attemptManager.getAttemptsByTest(test1).get(0);
        success &= attemptManager.deleteAttempt(attempt);
        return success;
    }
}
