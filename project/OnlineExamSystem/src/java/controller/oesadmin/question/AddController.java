/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.oesadmin.question;

import dao.CourseManager;
import dao.QuestionManager;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.Choice;
import model.Course;
import model.Question;
import util.servlet.ManagedServlet;

/**
 *
 * @author Quang Minh
 */
@WebServlet("/oes-admin/question/add")
@ServletSecurity(@HttpConstraint(rolesAllowed = "testmaster"))
public class AddController extends ManagedServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CourseManager cm = new CourseManager();
        List<Course> courses = cm.getAllCourses();
        request.setAttribute("courses", courses);
        getCorrespondingViewDispatcher().forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //--add question---//
        QuestionManager db = new QuestionManager();
        List<Question> questions = db.getAllQuestions();
        System.out.println("id: " + questions.get(questions.size() - 1).getId());
        HttpSession session = request.getSession(true);
        Account owner = (Account) session.getAttribute("currentUser");
        String content = request.getParameter("content");
        String courseID = request.getParameter("course");
        CourseManager cm = new CourseManager();
        Course course = cm.getCourse(courseID);
        Question question = new Question(owner, course, content);
        QuestionManager qm = new QuestionManager();

        long questionID = questions.get(questions.size() - 1).getId() + 1;

        //---add choices---//
        boolean result = false;
        for (int i = 0; i < 4; i++) {
            String answer = request.getParameter("answer" + (i + 1));
            String correct = request.getParameter("correct" + (i + 1));
            System.out.println("correct: " + correct);
            System.out.println("answer: " + answer);
            if (correct == null) {
                result = false;
            } else if (correct.equalsIgnoreCase("true")) {
                result = true;
            }
            Choice choice = new Choice(answer, result);
            question.addChoice(choice);
        }
        qm.addQuestion(question);
        redirect(response, "/oes-admin/question/add");
    }
}
