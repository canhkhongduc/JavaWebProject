/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.oesadmin.course;

import dao.CourseManager;
import java.io.IOException;
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
@WebServlet("/oes-admin/course/delete")
@ServletSecurity(@HttpConstraint(rolesAllowed = "admin"))
public class CourseDeleteController extends ManagedServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CourseManager courseManager = new CourseManager();
        
        // Extract parameters
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        
        if (id.isEmpty()) id = null;
        
        // Validation
        if (id == null) {
            response.sendError(400, "No course ID specified.");
            return;
        }
        Course course = courseManager.getCourse(id);
        if (course == null) {
            response.sendError(400, "Course ID does not exist.");
            return;
        }
        
        // Update course
        courseManager.deleteCourse(course);
        
        redirect(response, getServletURL(CourseController.class));
    }
}
