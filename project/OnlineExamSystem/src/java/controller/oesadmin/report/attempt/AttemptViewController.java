/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.oesadmin.report.attempt;

import dao.AttemptManager;
import dao.TestManager;
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
import model.Test;
import util.servlet.ManagedServlet;

/**
 *
 * @author Le Cao Nguyen
 */
@WebServlet("/oes-admin/report/attempt/view")
@ServletSecurity(
        @HttpConstraint(rolesAllowed = "testmaster"))
public class AttemptViewController extends ManagedServlet {

    private Long getAndValidateTestId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long testId;
        try {
            testId = Long.parseLong(request.getParameter("testId"));
            if (testId <= 0) {
                testId = null;
            }
        } catch (NumberFormatException ex) {
            testId = null;
        }
        return testId;
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Account owner = (Account) request.getSession().getAttribute("currentUser");
        TestManager testManager = new TestManager();
        AttemptManager attemptManager = new AttemptManager();
        
        // Get and validate test
        Long testId = getAndValidateTestId(request, response);
        if (testId == null) {
            response.sendError(400, "Invalid parameter testId.");
            return;
        }
        Test test = testManager.getTest(testId);
        if (test == null) {
            response.sendError(400, "Test does not exist.");
            return;
        }
        if (!owner.equals(test.getOwner())) {
            response.sendError(400, "Test does not belong to the current user.");
            return;
        }
        // Get viewMode
        String viewMode = request.getParameter("viewMode");
        if ((viewMode == null) || !(viewMode.equals("all") || viewMode.equals("latest"))) {
            viewMode = "latest";
        }
        
        List<Attempt> testAttempts = attemptManager.getAttemptsByTest(test);
        if (viewMode.equals("latest")) {
            testAttempts = attemptManager.filterLatestAttemptOfExaminee(testAttempts);
        }
        request.setAttribute("test", test);
        request.setAttribute("testAttempts", testAttempts);
        request.setAttribute("viewMode", viewMode);
        getCorrespondingViewDispatcher().forward(request, response);
    }
}
