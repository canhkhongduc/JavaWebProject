/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.oesadmin.test;

import dao.AccountManager;
import dao.CourseManager;
import dao.QuestionManager;
import dao.RoleManager;
import dao.TestManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
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
@WebServlet("/oes-admin/test/view")
@ServletSecurity(@HttpConstraint(rolesAllowed = "testmaster"))
public class TestViewController extends ManagedServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        TestManager testManager = new TestManager();
        Test test = testManager.getTest(id, true);
        RoleManager roleManager = new RoleManager();
        Account currentUser = (Account) request.getSession().getAttribute("currentUser");
        if (test == null) {
            redirect(response, getServletURL(TestController.class));
        } else if (test.getOwner().getUsername().equals(currentUser.getUsername()) || !test.getRestricted()) {
            List<Course> courses = new CourseManager().getAllCourses();
            List<Question> questions = new QuestionManager().getAllQuestions();
            List<Account> students = new AccountManager().getAccountsByRole(roleManager.getRole("student"));
            request.setAttribute("questions", questions);
            request.setAttribute("courses", courses);
            request.setAttribute("students", students);
            request.setAttribute("test", test);
            getCorrespondingViewDispatcher().forward(request, response);
        } else {
            redirect(response, getServletURL(TestController.class));
        }
    }
}
