/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Account;
import model.Question;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.hibernate.HibernateUtil;

/**
 *
 * @author Lam
 */
public class QuestionManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionManager.class);
    
    private final SessionFactory sessionFactory;

    public QuestionManager() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }
    
    public List<Question> getAllQuestions() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Question.class);
        List<Question> questionList = criteria.list();
        session.getTransaction().commit();
        return questionList;
    }
}
