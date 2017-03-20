/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.oesadmin.test;

import dao.AccountManager;
import dao.CourseManager;
import dao.QuestionManager;
import dao.RoleManager;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.Course;
import model.Question;
import model.Test;
import util.servlet.ManagedServlet;

/**
 *
 * @author Hai
 */
@WebServlet("/oes-admin/test/add")
@ServletSecurity(@HttpConstraint(rolesAllowed = {"admin", "testmaster"}))
public class TestAddController extends ManagedServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Course> courses = new CourseManager().getAllCourses();
        List<Question> questions = new QuestionManager().getAllQuestions();
        List<Account> students = new AccountManager().getAccountsByRole("student");
        request.setAttribute("questions", questions);
        request.setAttribute("courses", courses);
        request.setAttribute("students", students);
        getCorrespondingViewDispatcher().forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Test test = new Test();
        String name = request.getParameter("name");
        if (name == null || name.isEmpty())
            response.sendRedirect(getServletURL());
        test.setName(name);
    }
}
