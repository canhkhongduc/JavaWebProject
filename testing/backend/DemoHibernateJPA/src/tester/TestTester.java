/*
 * Copyright 2017 Le Cao Nguyen
 */
package tester;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import model.Account;
import model.Attempt;
import model.Choice;
import model.Course;
import model.Question;
import model.Test;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import util.hibernate.HibernateUtil;

/**
 *
 * @author Le Cao Nguyen
 */
public class TestTester implements Runnable {

    private final SessionFactory sessionFactory;

    public TestTester() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    private void initData() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Course course = (Course) new Course("PRO192", "OOP with Java");
        session.save(course);
        
        Account owner = (Account) session.createCriteria(Account.class).add(Restrictions.eq("username", "admin")).uniqueResult();
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

        session.getTransaction().commit();
    }

    private void createAttempts() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Test test = (Test) session.createCriteria(Test.class).list().get(0);
        Account examinee = (Account) session.createCriteria(Account.class).add(Restrictions.eq("username", "nguyen")).uniqueResult();
        Attempt a1 = new Attempt(examinee, test);
        Set<Choice> allChoices = test.getQuestions().stream().map(Question::getChoices).flatMap(Set::stream).collect(Collectors.toSet());
        Random random = new Random();
        allChoices.stream().filter((choice) -> random.nextBoolean()).forEach((choice) -> a1.addChoice(choice));
        session.save(a1);
        
        session.getTransaction().commit();
    }

    @Override
    public void run() {
        initData();
        createAttempts();
    }

}
