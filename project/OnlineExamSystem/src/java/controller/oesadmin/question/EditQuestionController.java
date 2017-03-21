/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.oesadmin.question;

import dao.ChoiceManager;
import dao.CourseManager;
import dao.QuestionManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
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
@WebServlet("/oes-admin/question/edit")
public class EditQuestionController extends ManagedServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        QuestionManager qm = new QuestionManager();
        long questionID = Long.parseLong(request.getParameter("id"));
        Question question = qm.getQuestion(questionID, true);
        Set<Choice> choices = question.getChoices();
        request.setAttribute("question", question);
        request.setAttribute("choices", choices);
        getCorrespondingViewDispatcher().forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        QuestionManager qm = new QuestionManager();
        HttpSession session = request.getSession(true);
        Account owner = (Account) session.getAttribute("currentUser");
        long questionID = Long.parseLong(request.getParameter("id"));
        Question question = qm.getQuestion(questionID);
        question.setContent(request.getParameter("content"));
        boolean result = false;
        ChoiceManager cm = new ChoiceManager();
        List<Choice> choices = cm.getChoices(question);
        for (int i = 0; i < choices.size(); i++) {
            String answer = request.getParameter("answer" + (i + 1));
            String correct = request.getParameter("correct" + (i + 1));
            if (correct == null) {
                result = false;
            } else if (correct.equalsIgnoreCase("true")) {
                result = true;
            }
            //Choice choice = new Choice(answer, result);
            choices.get(i).setContent(answer);
            choices.get(i).setCorrect(result);
            cm.updateChoice(choices.get(i));
        }
        qm.updateQuestion(question);
        redirect(response, getServletURL(QuestionController.class));
    }
}
