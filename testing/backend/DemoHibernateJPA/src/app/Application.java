/*
 * Copyright 2017 Le Cao Nguyen
 */
package app;

import tester.AccountTester;
import tester.AttemptTester;
import tester.TestTester;

/**
 *
 * @author Le Cao Nguyen
 */
public class Application implements Runnable {

    @Override
    public void run() {
        /*
        AccountTester accountTester = new AccountTester();
        accountTester.run();
        TestTester testTester = new TestTester();
        testTester.run();
         */
        AttemptTester attemptTester = new AttemptTester();
        attemptTester.run();
    }

    public static void main(String[] args) {
        new Application().run();
        System.exit(0);
    }
}
