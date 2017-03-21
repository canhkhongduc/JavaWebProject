/*
 * Copyright © 2017 Six Idiots Team
 */
package dao;

import java.util.List;
import model.Account;
import model.Attempt;
import model.Course;
import model.Question;
import model.Test;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import util.hibernate.transaction.TransactionPerformer;

/**
 *
 * @author nguyen
 */
public class QuestionManager extends TransactionPerformer {

    public static void initializeProperties(Question question) {
        Hibernate.initialize(question.getChoices());
    }

    public List<Question> getAllQuestions() {
        return getAllQuestions(false);
    }

    public List<Question> getAllQuestions(boolean fetch) {
        return performTransaction((session) -> {
            Criteria criteria = session.createCriteria(Question.class);
            criteria.addOrder(Order.asc("id"));
            List<Question> questions = criteria.list();
            if (fetch) {
                questions.forEach((question) -> {
                    initializeProperties(question);
                });
            }
            return questions;
        });
    }

    public List<Question> getQuestionsByCourse(Course course) {
        return getQuestionsByCourse(course, false);
    }

    public List<Question> getQuestionsByCourse(Course course, boolean fetch) {
        return performTransaction((session) -> {
            Criteria criteria = session.createCriteria(Question.class);
            criteria.addOrder(Order.asc("id"));
            criteria.add(Restrictions.eq("course", course));
            List<Question> questions = criteria.list();
            if (fetch) {
                questions.forEach((question) -> {
                    initializeProperties(question);
                });
            }
            return questions;
        });
    }

    public List<Question> getQuestionsByOwner(Account owner) {
        return getQuestionsByOwner(owner, false);
    }

    public List<Question> getQuestionsByOwner(Account owner, boolean fetch) {
        return performTransaction((session) -> {
            Criteria criteria = session.createCriteria(Question.class);
            criteria.addOrder(Order.asc("id"));
            criteria.add(Restrictions.eq("owner", owner));
            List<Question> questions = criteria.list();
            if (fetch) {
                questions.forEach((question) -> {
                    initializeProperties(question);
                });
            }
            return questions;
        });
    }

    public Question getQuestion(Long id) {
        return performTransaction((session) -> {
            Question question = (Question) session.get(Question.class, id);
            return question;
        });
    }
    
    public Question getQuestion(Long id, boolean fetch) {
        return performTransaction((session) -> {
            Question question = (Question) session.get(Question.class, id);
            if(fetch){
                Hibernate.initialize(question.getChoices());
            }
            return question;
        });
    }

    public boolean addQuestion(Question question) {
        return performTransaction((session) -> {
            session.save(question);
        });
    }
    
    public boolean saveQuestion(Question question) {
        return performTransaction((session) -> {
            session.save(question);
        });
    }

    public boolean updateQuestion(Question question) {
        return performTransaction((session) -> {
            session.update(question);
        });
    }

    public boolean deleteQuestion(Question question) {
        if (!removeAllReferences(question)) {
            throw new HibernateException("Could not remove all references to question.");
        }
        return performTransaction((session) -> {
            session.delete(question);
        });
    }

    /**
     * Reference removal for question:<br>
     * - All tests that have this question -> remove question<br>
     * - If an attempt has one or several choices belongs to this question ->
     * remove those choices from attempt<br>
     */
    public boolean removeAllReferences(Question question) {
        return performTransaction((session) -> {
            List<Test> tests = session.createCriteria(Test.class).createAlias("questions", "questionsAlias")
                    .add(Restrictions.eq("questionsAlias.id", question.getId())).list();
            tests.forEach((test) -> {
                test.removeQuestion(question);
                session.update(test);
            });
            List<Attempt> attempts = session.createCriteria(Attempt.class).list();
            attempts.forEach((attempt) -> {
                attempt.getChoices().removeIf((choice) -> choice.getQuestion().equals(question));
                session.update(attempt);
            });
        });
    }
}
