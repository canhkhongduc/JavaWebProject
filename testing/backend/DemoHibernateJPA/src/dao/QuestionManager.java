/*
 * Copyright © 2017 Six Idiots Team
 */
package dao;

import java.util.List;
import model.Course;
import model.Question;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import util.hibernate.transaction.TransactionPerformer;

/**
 *
 * @author nguyen
 */
public class QuestionManager extends TransactionPerformer {

    public List<Question> getAllQuestions() {
        return performTransaction((session) -> {
            Criteria criteria = session.createCriteria(Question.class);
            criteria.addOrder(Order.asc("id"));
            return criteria.list();
        });
    }

    public List<Question> getQuestions(Course course) {
        return performTransaction((session) -> {
            Criteria criteria = session.createCriteria(Question.class);
            criteria.addOrder(Order.asc("id"));
            criteria.add(Restrictions.eq("course", course));
            return criteria.list();
        });
    }
    
    public Question getQuestion(Long id) {
        return performTransaction((session) -> {
            return (Question) session.get(Question.class, id);
        });
    }
}
