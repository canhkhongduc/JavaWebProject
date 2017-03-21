/*
 * Copyright 2017 Le Cao Nguyen
 */
package tester;

import dao.AccountManager;
import dao.CourseManager;
import dao.QuestionManager;
import dao.RoleManager;
import dao.TestManager;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import model.Account;
import model.Course;
import model.Question;
import model.Test;
import util.hibernate.transaction.TransactionPerformer;

/**
 *
 * @author Le Cao Nguyen
 */
public class TestTester extends TransactionPerformer implements Runnable {
    private final QuestionManager questionManager = new QuestionManager();
    private final TestManager testManager = new TestManager();
    private final CourseManager courseManager = new CourseManager();
    private final AccountManager accountManager = new AccountManager();
    private final RoleManager roleManager = new RoleManager();
    
    public boolean createTest() {
        boolean success = true;
        Course c01 = courseManager.getCourse("C01");
        Course c02 = courseManager.getCourse("C02");
        List<Question> questions = questionManager.getQuestionsByCourse(c01);
        List<Question> questions2 = questionManager.getQuestionsByCourse(c02);
        Account testmaster1 = accountManager.getAccount("testmaster1");
        Account testmaster2 = accountManager.getAccount("testmaster2");
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, 3, 21, 9, 0, 0);
        Date joinStartTime = calendar.getTime();
        calendar.set(2017, 3, 21, 9, 10, 0);
        Date joinEndTime = calendar.getTime();
        
        Test test1 = new Test(testmaster1, "Test 1", joinStartTime, joinEndTime, 30, 3, true, c01);
        questions.forEach((q) -> {
            test1.addQuestion(q);
        });
        List<Account> students = accountManager.getAccountsByRole(roleManager.getRole("student"));
        for (Account examinee : students) {
            test1.addExaminee(examinee);
        }
        
        calendar.set(2017, 3, 22, 9, 0, 0);
        joinStartTime = calendar.getTime();
        calendar.set(2017, 3, 22, 9, 10, 0);
        joinEndTime = calendar.getTime();
        
        Test test2 = new Test(testmaster2, "Test 2", joinStartTime, joinEndTime, 30, 1, true, c02);
        questions2.forEach((q) -> {
            test2.addQuestion(q);
        });
        for (Account examinee : students) {
            test2.addExaminee(examinee);
        }
        
        success &= testManager.saveTest(test1);
        success &= testManager.saveTest(test2);
        return success;
    }

    public boolean updateTestQuestions() {
        boolean success = true;
        Course c01 = courseManager.getCourse("C01");
        Test test1 = testManager.getTestsByCourse(c01, true).get(0);
        Question q1 = questionManager.getQuestionsByCourse(c01).get(0);
        test1.removeQuestion(q1);
        success &= testManager.updateTest(test1);
        return success;
    }
    
    public boolean deleteTest() {
        boolean success = true;
        List<Test> tests = testManager.getAllTests();
        for (Test test : tests) {
            success &= testManager.deleteTest(test);
        }
        return success;
    }
    
    @Override
    public void run() {
        System.out.println("Creating test... " + (createTest() ? "Success!" : "Failed!"));
        System.out.println("Updating test questions... " + (updateTestQuestions() ? "Success!" : "Failed!"));
        System.out.println("Deleting test... " + (deleteTest() ? "Success!" : "Failed!"));
    }

    

}
