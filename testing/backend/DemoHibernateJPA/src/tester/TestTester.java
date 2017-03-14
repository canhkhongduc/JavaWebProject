/*
 * Copyright 2017 Le Cao Nguyen
 */
package tester;

import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import model.Account;
import model.Attempt;
import model.Choice;
import model.Course;
import model.Question;
import model.Test;
import util.hibernate.transaction.TransactionPerformer;

/**
 *
 * @author Le Cao Nguyen
 */
public class TestTester extends TransactionPerformer implements Runnable {

    private void initData() {
        performTransaction((session) -> {
            Course course = (Course) new Course("PRO192", "OOP with Java");
            session.save(course);

            Account owner = (Account) session.get(Account.class, "admin");
            Test test = new Test();
            test.setOwner(owner);
            test.setName("PRO192 FE (Spring 2017)");

            Question q1 = new Question(owner, course, "Question 1");
            q1.addChoice(new Choice("Answer 1.1", Boolean.TRUE));
            q1.addChoice(new Choice("Answer 1.2", Boolean.FALSE));
            q1.addChoice(new Choice("Answer 1.3", Boolean.FALSE));
            q1.addChoice(new Choice("Answer 1.4", Boolean.FALSE));

            Question q2 = new Question(owner, course, "Question 2");
            q2.addChoice(new Choice("Answer 2.1", Boolean.FALSE));
            q2.addChoice(new Choice("Answer 2.2", Boolean.TRUE));
            q2.addChoice(new Choice("Answer 2.3", Boolean.FALSE));
            q2.addChoice(new Choice("Answer 2.4", Boolean.FALSE));

            test.addQuestion(q1);
            test.addQuestion(q2);

            session.save(test);
        });
    }

    private void createAttempts() {
        performTransaction((session) -> {
            Test test = (Test) session.createCriteria(Test.class).list().get(0);
            Account examinee = (Account) session.get(Account.class, "nguyen");
            Attempt a1 = new Attempt(examinee, test);
            Set<Choice> allChoices = test.getQuestions().stream().map(Question::getChoices).flatMap(Set::stream).collect(Collectors.toSet());
            Random random = new Random();
            allChoices.stream().filter((choice) -> random.nextBoolean()).forEach((choice) -> a1.addChoice(choice));
            session.save(a1);
        });
    }

    @Override
    public void run() {
        initData();
        createAttempts();
    }

}
