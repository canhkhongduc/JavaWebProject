/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.oesadmin.question;

import dao.QuestionManager;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Question;
import util.servlet.ManagedServlet;

/**
 *
 * @author Quang Minh
 */
@WebServlet("/oes-admin/question/delete")
@ServletSecurity(@HttpConstraint(rolesAllowed = "testmaster"))
public class RemoveController extends ManagedServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String questionID = request.getParameter("id");
        QuestionManager qm = new QuestionManager();
        Question question = qm.getQuestion(Long.parseLong(questionID));
        boolean del = qm.deleteQuestion(question);
        redirect(response, getServletURL(QuestionController.class));
    }
}
