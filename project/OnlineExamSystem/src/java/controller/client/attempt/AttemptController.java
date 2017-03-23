/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.client.attempt;

import dao.AttemptManager;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.Attempt;
import util.servlet.ManagedServlet;

/**
 *
 * @author Le Cao Nguyen
 */
@WebServlet("/client/attempt")
@ServletSecurity(@HttpConstraint(rolesAllowed = "student"))
public class AttemptController extends ManagedServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Account examinee = (Account) request.getSession().getAttribute("currentUser");
        AttemptManager attemptManager = new AttemptManager();
        
        // Get viewMode
        String viewMode = request.getParameter("viewMode");
        if ((viewMode == null) || !(viewMode.equals("all") || viewMode.equals("latest"))) {
            viewMode = "latest";
        }
        
        List<Attempt> examineeAttempts = attemptManager.getAttemptsByExaminee(examinee);
        if (viewMode.equals("latest")) {
            examineeAttempts = attemptManager.filterLatestAttemptOfTest(examineeAttempts);
        }
        request.setAttribute("examineeAttempts", examineeAttempts);
        request.setAttribute("viewMode", viewMode);
        getCorrespondingViewDispatcher().forward(request, response);
    }
    
}
