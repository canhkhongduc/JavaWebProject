/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.oesadmin.test;

import dao.TestManager;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.Test;
import util.servlet.ManagedServlet;

/**
 *
 * @author Hai
 */
@WebServlet("/oes-admin/test/delete")
@ServletSecurity(
        @HttpConstraint(rolesAllowed = "testmaster"))
public class TestDeleteController extends ManagedServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account currentUser = (Account) request.getSession().getAttribute("currentUser");
        TestManager testManager = new TestManager();
        String testId = request.getParameter("id");
        Test test = testManager.getTest(Long.parseLong(testId));
        if (test == null) {
            response.sendError(400, "Invalid test ID.");
            return;
        }
        if (!test.getOwner().equals(currentUser)) {
            response.sendError(403, "You do not have permission to delete the test.");
            return;
        }
        testManager.deleteTest(test);
        redirect(response, getServletURL(TestController.class));
    }
}
