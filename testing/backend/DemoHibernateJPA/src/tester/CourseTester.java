/*
 * Copyright 2017 Le Cao Nguyen
 */
package tester;

import dao.CourseManager;
import java.util.ArrayList;
import java.util.List;
import model.Course;
import util.hibernate.transaction.TransactionPerformer;

/**
 *
 * @author Le Cao Nguyen
 */
public class CourseTester extends TransactionPerformer implements Runnable {
    private final CourseManager courseManager = new CourseManager();
    
    @Override
    public void run() {
        System.out.println("Creating courses... " + (createCourses() ? "Success!" : "Failed!"));
        System.out.println("Updating courses... " + (updateCourses() ? "Success!" : "Failed!"));
        System.out.println("Updating courses... " + (deleteCourses() ? "Success!" : "Failed!"));
    }

    public boolean createCourses() {
        boolean success = true;
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("C01", "Course 1"));
        courses.add(new Course("C02", "Course 2"));
        for (Course course : courses) {
            success &= courseManager.saveCourse(course);
        }
        return success;
    }

    public boolean updateCourses() {
        boolean success = true;
        Course c02 = courseManager.getCourse("C02");
        c02.setName("Course 2 (new)");
        success &= courseManager.updateCourse(c02);
        return success;
    }

    public boolean deleteCourses() {
        boolean success = true;
        Course c01 = courseManager.getCourse("C01");
        success &= courseManager.deleteCourse(c01);
        List<Course> courses = courseManager.getAllCourses();
        for (Course course : courses) {
            success &= courseManager.deleteCourse(course);
        }
        return success;
    }

}
