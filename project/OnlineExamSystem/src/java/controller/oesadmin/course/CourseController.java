/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.oesadmin.course;

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
 * @author Hai
 */
@WebServlet("/oes-admin/course")
@ServletSecurity(@HttpConstraint(rolesAllowed = "admin"))
public class CourseController extends ManagedServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CourseManager courseManager = new CourseManager();
        List<Course> courses = courseManager.getAllCourses();
        request.setAttribute("courses", courses);
        getCorrespondingViewDispatcher().forward(request, response);
    }
}
