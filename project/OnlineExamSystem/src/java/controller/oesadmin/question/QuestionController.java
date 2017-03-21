/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.oesadmin.question;

import dao.QuestionManager;
import java.io.IOException;
import java.util.List;
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
@WebServlet("/oes-admin/question")
@ServletSecurity(@HttpConstraint(rolesAllowed = "testmaster"))
public class QuestionController extends ManagedServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        QuestionManager qm = new QuestionManager();
        List<Question> questions = qm.getAllQuestions();
        request.setAttribute("questions", questions);
        getCorrespondingViewDispatcher().forward(request, response);
    }
}
