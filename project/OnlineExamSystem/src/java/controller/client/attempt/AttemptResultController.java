/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.client.attempt;

import dao.AttemptManager;
import dao.TestManager;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Attempt;
import model.Test;
import util.servlet.ManagedServlet;

/**
 *
 * @author Niles
 */
@WebServlet("/client/attempt/result")
@ServletSecurity(@HttpConstraint(rolesAllowed = "student"))
public class AttemptResultController extends ManagedServlet {

    private Attempt getAttemptFromRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String attemptIdStr = request.getParameter("attemptId");
        long attemptId;
        try {
            attemptId = Long.parseLong(attemptIdStr);
        } catch (NumberFormatException ex) {
            attemptId = -1;
        }
        if (attemptId <= 0) {
            response.sendError(400, "Invalid attempt ID.");
            return null;
        }
        AttemptManager attemptManager = new AttemptManager();
        Attempt attempt = attemptManager.getAttempt(attemptId, true);
        if (attempt == null) {
            response.sendError(400, "Attempt ID does not exist.");
            return null;
        }
        return attempt;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Attempt attempt = getAttemptFromRequest(request, response);
        if (attempt != null) {
            TestManager testManager = new TestManager();
            Test test = testManager.getTest(attempt.getTest().getId(), true);
            request.setAttribute("test", test);
            request.setAttribute("attempt", attempt);
            getCorrespondingViewDispatcher().forward(request, response);
        }
    }
}
