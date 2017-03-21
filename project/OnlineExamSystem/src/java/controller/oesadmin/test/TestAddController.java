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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@ServletSecurity(@HttpConstraint(rolesAllowed = "testmaster"))
public class TestAddController extends ManagedServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Course> courses = new CourseManager().getAllCourses();
        List<Question> questions = new QuestionManager().getAllQuestions();
        List<Account> students = new AccountManager().getAccountsByRole(new RoleManager().getRole("student"));
        request.setAttribute("questions", questions);
        request.setAttribute("courses", courses);
        request.setAttribute("students", students);
        getCorrespondingViewDispatcher().forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Test test = new Test();
        TestManager testManager = new TestManager();
        CourseManager courseManager = new CourseManager();
        QuestionManager questionManager = new QuestionManager();
        AccountManager accountManager = new AccountManager();
        Account owner = (Account) request.getSession().getAttribute("currentUser");

        test.setName(request.getParameter("name"));
        test.setCourse(courseManager.getCourse(request.getParameter("course")));
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String[] time = request.getParameter("joinTime").split(" - ");
        try {
            test.setJoinStartTime(df.parse(time[0]));
            test.setJoinEndTime(df.parse(time[1]));
        } catch (ParseException ex) {
            Logger.getLogger(TestAddController.class.getName()).log(Level.SEVERE, null, ex);
        }
        test.setTimeLength(Integer.parseInt(request.getParameter("length")));
        test.setAttemptLimit(Integer.parseInt(request.getParameter("attempt")));
        test.setRestricted((request.getParameter("restricted") != null));
        String[] selectedQuestions = request.getParameterValues("selectedQuestion");
        if (selectedQuestions != null) {
            for (String question : selectedQuestions) {
                test.addQuestion(questionManager.getQuestion(Long.parseLong(question),true));
            }
        }
        if (test.getRestricted()) {
            String[] selectedStudents = request.getParameterValues("selectedStudent");
            if (selectedStudents != null) {
                for (String student : selectedStudents) {
                    test.addExaminee(accountManager.getAccount(student));
                }
            }
        }
        test.setOwner(accountManager.getAccount(owner.getUsername()));
        testManager.saveTest(test);
        redirect(response, getServletURL(TestController.class));
    }

}
