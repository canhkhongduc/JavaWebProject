/*
 * Copyright © 2017 Six Idiots Team
 */
package dao;

import java.util.List;
import model.Course;
import model.Question;
import model.Test;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import util.hibernate.transaction.TransactionPerformer;

/**
 *
 * @author Le Cao Nguyen
 */
public class CourseManager extends TransactionPerformer {

    public List<Course> getAllCourses() {
        return performTransaction((session) -> {
            Criteria criteria = session.createCriteria(Course.class);
            criteria.addOrder(Order.asc("id"));
            return criteria.list();
        });
    }

    public Course getCourse(String id) {
        return performTransaction((session) -> {
            return (Course) session.get(Course.class, id);
        });
    }

    public boolean saveCourse(Course course) {
        return performTransaction((session) -> {
            session.save(course);
        });
    }

    public boolean updateCourse(Course course) {
        return performTransaction((session) -> {
            session.update(course);
        });
    }

    public boolean deleteCourse(Course course) {
        if (!removeAllReferences(course)) {
            throw new HibernateException("Could not remove all references to course.");
        }
        return performTransaction((session) -> {
            session.delete(course);
        });
    }

    /**
     * Reference removal for course:<br>
     * - All questions belong to this course -> set course = NULL<br>
     * - All tests belong to this course -> set course = NULL<br>
     */
    public boolean removeAllReferences(Course course) {
        return performTransaction((session) -> {
            List<Question> assignedQuestions = session.createCriteria(Question.class)
                    .add(Restrictions.eq("course", course)).list();
            List<Test> assignedTests = session.createCriteria(Test.class)
                    .add(Restrictions.eq("course", course)).list();
            assignedQuestions.forEach((question) -> {
                question.setCourse(null);
                session.update(question);
            });
            assignedTests.forEach((test) -> {
                test.setCourse(null);
                session.update(test);
            });
        });
    }
}
