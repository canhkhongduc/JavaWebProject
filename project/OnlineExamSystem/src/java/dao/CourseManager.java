/*
 * Copyright © 2017 Six Idiots Team
 */
package dao;

import java.util.List;
import model.Course;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import util.hibernate.transaction.TransactionPerformer;

/**
 *
 * @author nguyen
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
        return performTransaction((session) -> {
            session.delete(course);
        });
    }
}
