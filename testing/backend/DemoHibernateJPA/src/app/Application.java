/*
 * Copyright 2017 Le Cao Nguyen
 */
package app;

import java.util.logging.Level;
import java.util.logging.Logger;
import tester.AccountTester;
import tester.AttemptTester;
import tester.CourseTester;
import tester.QuestionTester;
import tester.RelationshipTester;
import tester.RoleTester;
import tester.TestTester;
import util.hibernate.HibernateUtil;

/**
 *
 * @author Le Cao Nguyen
 */
public class Application implements Runnable {

    @Override
    public void run() {
        try {
            Class.forName(HibernateUtil.class.getName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("---Role testing---");
        RoleTester roleTester = new RoleTester();
        roleTester.run();
        roleTester.createRoles();
        
        System.out.println("---Account testing---");
        AccountTester accountTester = new AccountTester();
        accountTester.run();
        accountTester.createAccounts();
        
        System.out.println("---Course testing---");
        CourseTester courseTester = new CourseTester();
        courseTester.run();
        courseTester.createCourses();
        
        System.out.println("---Question testing---");
        QuestionTester questionTester = new QuestionTester();
        questionTester.run();
        questionTester.createQuestions();
        
        System.out.println("---Test testing---");
        TestTester testTester = new TestTester();
        testTester.run();
        testTester.createTest();
        
        System.out.println("---Attempt testing---");
        AttemptTester attemptTester = new AttemptTester();
        attemptTester.run();
        attemptTester.createAttempt();
        
        System.out.println("---Relationship testing---");
        RelationshipTester relationshipTester = new RelationshipTester();
        relationshipTester.run();
    }

    public static void main(String[] args) {
        new Application().run();
        System.exit(0);
    }
}
