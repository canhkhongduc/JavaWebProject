/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.client.course;

import dao.CourseManager;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Course;
import util.servlet.ManagedServlet;

/**
 *
 * @author Le Cao Nguyen
 */
@WebServlet("/client/course")
@ServletSecurity(@HttpConstraint(rolesAllowed = "student"))
public class CourseController extends ManagedServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CourseManager cm = new CourseManager();
        List<Course> courses = cm.getAllCourses();
        request.setAttribute("courses", courses);
        getCorrespondingViewDispatcher().forward(request, response);
    }
    
}
