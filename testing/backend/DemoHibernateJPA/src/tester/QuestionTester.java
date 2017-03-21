/*
 * Copyright 2017 Le Cao Nguyen
 */
package tester;

import dao.AccountManager;
import dao.CourseManager;
import dao.QuestionManager;
import java.util.List;
import model.Account;
import model.Choice;
import model.Course;
import model.Question;
import util.hibernate.transaction.TransactionPerformer;

/**
 *
 * @author Le Cao Nguyen
 */
public class QuestionTester extends TransactionPerformer implements Runnable {
    private final QuestionManager questionManager = new QuestionManager();
    private final AccountManager accountManager = new AccountManager();
    private final CourseManager courseManager = new CourseManager();
    
    public boolean createQuestions() {
        boolean success = true;
        Account testmaster1 = accountManager.getAccount("testmaster1");
        Course c01 = courseManager.getCourse("c01");
        Course c02 = courseManager.getCourse("c02");
        
        Question q1 = new Question(testmaster1, c01, "Question 1");
        q1.addChoice(new Choice("Choice 1.1", true));
        q1.addChoice(new Choice("Choice 1.2", false));
        q1.addChoice(new Choice("Choice 1.3", false));
        q1.addChoice(new Choice("Choice 1.4", false));
        
        Question q2 = new Question(testmaster1, c01, "Question 2");
        q2.addChoice(new Choice("Choice 2.1", true));
        q2.addChoice(new Choice("Choice 2.2", false));
        q2.addChoice(new Choice("Choice 2.3", true));
        q2.addChoice(new Choice("Choice 2.4", false));
        
        Question q3 = new Question(testmaster1, c01, "Question 3");
        q3.addChoice(new Choice("Choice 3.1", false));
        q3.addChoice(new Choice("Choice 3.2", false));
        q3.addChoice(new Choice("Choice 3.3", true));
        q3.addChoice(new Choice("Choice 3.4", false));
        
        Question q4 = new Question(testmaster1, c02, "Question 4");
        q4.addChoice(new Choice("Choice 4.1", false));
        q4.addChoice(new Choice("Choice 4.2", false));
        q4.addChoice(new Choice("Choice 4.3", true));
        q4.addChoice(new Choice("Choice 4.4", false));
        
        Question q5 = new Question(testmaster1, c02, "Question 5");
        q5.addChoice(new Choice("Choice 5.1", false));
        q5.addChoice(new Choice("Choice 5.2", false));
        q5.addChoice(new Choice("Choice 5.3", true));
        q5.addChoice(new Choice("Choice 5.4", false));
        
        success &= questionManager.saveQuestion(q1);
        success &= questionManager.saveQuestion(q2);
        success &= questionManager.saveQuestion(q3);
        success &= questionManager.saveQuestion(q4);
        success &= questionManager.saveQuestion(q5);
        
        return success;
    }
    
    public boolean updateQuestionInfo() {
        boolean success = true;
        Course c01 = courseManager.getCourse("c01");
        Course c02 = courseManager.getCourse("c02");
        Account testmaster2 = accountManager.getAccount("testmaster2");
        
        List<Question> questions = questionManager.getQuestionsByCourse(c01);
        Question q = questions.get(0);
        
        q.setCourse(c02);
        q.setOwner(null);
        success &= questionManager.updateQuestion(q);
        
        q.setCourse(c01);
        q.setOwner(testmaster2);
        success &= questionManager.updateQuestion(q);
        
        return success;
    }
    
    public boolean updateQuestionChoices() {
        boolean success = true;
        Course c01 = courseManager.getCourse("c01");
        List<Question> questions = questionManager.getQuestionsByCourse(c01, true);
        Question q = questions.get(0);
        
        Choice choice = q.getChoices().stream().findFirst().get();
        q.removeChoice(choice);
        success &= questionManager.updateQuestion(q);
        
        choice = q.getChoices().stream().findFirst().get();
        choice.setCorrect(true);
        success &= questionManager.updateQuestion(q);
        
        q.addChoice(new Choice("Choice 1.5", false));
        success &= questionManager.updateQuestion(q);
        
        return success;
    }
    
    public boolean deleteQuestions() {
        boolean success = true;
        List<Question> questions = questionManager.getAllQuestions();
        
        for (Question question : questions) {
            success &= questionManager.deleteQuestion(question);
        }
        return success;
    }

    @Override
    public void run() {
        System.out.println("Creating questions... " + (createQuestions() ? "Success!" : "Failed!"));
        System.out.println("Updating a question information... " + (updateQuestionInfo() ? "Success!" : "Failed!"));
        System.out.println("Updating a question choices... " + (updateQuestionChoices()? "Success!" : "Failed!"));
        System.out.println("Deleting questions... " + (deleteQuestions() ? "Success!" : "Failed!"));
    }
}
