package dao;

import util.hibernate.HibernateUtil;
import java.util.List;
import model.Course;
import model.Question;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author nguyen
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
        List<Question> questions = criteria.list();
        session.getTransaction().commit();
        return questions;
    }
    
    public List<Question> getQuestionsByCourse(Course course) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Question.class);
        criteria.add(Restrictions.eq("course", course));
        List<Question> questions = criteria.list();
        session.getTransaction().commit();
        return questions;
    }
}
