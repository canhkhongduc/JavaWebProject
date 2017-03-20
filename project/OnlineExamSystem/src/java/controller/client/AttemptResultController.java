/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.client;

import dao.AttemptManager;
import dao.TestManager;
import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Attempt;
import model.Question;
import model.Test;
import org.hibernate.Hibernate;
import util.servlet.ManagedServlet;

/**
 *
 * @author Niles
 */
@WebServlet("/client/attempt/result")
public class AttemptResultController extends ManagedServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String attemptId = req.getParameter("attemptId");

        AttemptManager attM = new AttemptManager();
        TestManager tm = new TestManager();
        Attempt attempt = attM.getAttempt(Long.parseLong(attemptId));
        Test test = tm.getTest(attempt.getTest().getId());

        req.setAttribute("test", test);
        req.setAttribute("attempt", attempt);
        getCorrespondingViewDispatcher().forward(req, resp);
    }

}
