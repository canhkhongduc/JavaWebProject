/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.oesadmin.question;

import dao.ChoiceManager;
import dao.QuestionManager;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.Choice;
import model.Question;
import util.servlet.ManagedServlet;

/**
 *
 * @author Quang Minh
 */
@WebServlet("/oes-admin/question/edit")
@ServletSecurity(@HttpConstraint(rolesAllowed = "testmaster"))
public class EditQuestionController extends ManagedServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        QuestionManager qm = new QuestionManager();
        long questionID = Long.parseLong(request.getParameter("id"));
        Question question = qm.getQuestion(questionID, true);
        if (question == null) {
            response.sendError(400, "Invalid question ID.");
            return;
        }
        Set<Choice> choices = question.getChoices();
        request.setAttribute("question", question);
        request.setAttribute("choices", choices);
        getCorrespondingViewDispatcher().forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account currentUser = (Account) request.getSession().getAttribute("currentUser");
        QuestionManager qm = new QuestionManager();
        long questionID = Long.parseLong(request.getParameter("id"));
        Question question = qm.getQuestion(questionID);
        if (question == null) {
            response.sendError(400, "Invalid question ID.");
            return;
        }
        if (!question.getOwner().equals(currentUser)) {
            response.sendError(403, "You do not have permission to edit the question.");
            return;
        }
        
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
