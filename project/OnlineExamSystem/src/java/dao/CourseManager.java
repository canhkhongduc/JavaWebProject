package dao;

import util.hibernate.HibernateUtil;
import java.util.List;
import model.Course;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author nguyen
 */
public class CourseManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseManager.class);
    
    private final SessionFactory sessionFactory;

    public CourseManager() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<Course> getAllCourse() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Course.class);
        List<Course> courses = criteria.list();
        session.getTransaction().commit();
        return courses;
    }
    
    public Course getCourse(String id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Course course = (Course) session.get(Course.class, id);
        session.getTransaction().commit();
        return course;
    }
}
