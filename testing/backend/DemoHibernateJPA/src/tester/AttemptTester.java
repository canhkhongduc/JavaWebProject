/*
 * Copyright 2017 Le Cao Nguyen
 */
package tester;

import dao.AccountManager;
import dao.AttemptManager;
import dao.CourseManager;
import dao.TestManager;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import model.Account;
import model.Attempt;
import model.Choice;
import model.Course;
import model.Test;
import util.hibernate.transaction.TransactionPerformer;

/**
 *
 * @author Le Cao Nguyen
 */
public class AttemptTester extends TransactionPerformer implements Runnable {
    private final TestManager testManager = new TestManager();
    private final AttemptManager attemptManager = new AttemptManager();
    private final AccountManager accountManager = new AccountManager();
    private final CourseManager courseManager = new CourseManager();
    
    public boolean createAttempt() {
        boolean success = true;
        Course c01 = courseManager.getCourse("C01");
        Test test1 = testManager.getTestsByCourse(c01, true).get(0);
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, 21, 3, 9, 5, 0);
        Date startTime = calendar.getTime();
        calendar.set(2017, 21, 3, 9, 20, 0);
        Date endTime = calendar.getTime();
        
        Account student1 = accountManager.getAccount("student1");
        Attempt attempt = new Attempt(student1, test1, startTime, endTime, 10.0);
        
        Set<Choice> correctChoices = test1.getQuestions().stream().map((question) -> question.getChoices().stream()
                .filter((choice) -> choice.isCorrect()).collect(Collectors.toSet()))
                .flatMap(Set::stream).collect(Collectors.toSet());
        correctChoices.forEach((choice) -> attempt.addChoice(choice));
        
        success &= attemptManager.saveAttempt(attempt);
        return success;
    }
    
    public boolean deleteAttempt() {
        boolean success = true;
        List<Attempt> attempts = attemptManager.getAllAttempts();
        for (Attempt attempt : attempts) {
            success &= attemptManager.deleteAttempt(attempt);
        }
        return success;
    }
    
    @Override
    public void run() {
        System.out.println("Creating attempt... " + (createAttempt() ? "Success!" : "Failed!"));
        System.out.println("Deleting attempt... " + (deleteAttempt() ? "Success!" : "Failed!"));
    }

    

}
