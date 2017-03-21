/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.client.course;

import dao.CourseManager;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
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
public class CourseController extends ManagedServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
    
    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CourseManager cm = new CourseManager();
        List<Course> courses = cm.getAllCourses();
        req.setAttribute("courses", courses);
        getCorrespondingViewDispatcher().forward(req, resp);
    }
    
}
