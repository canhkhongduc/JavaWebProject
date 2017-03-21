/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.oesadmin.report;

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
import model.Test;
import util.servlet.ManagedServlet;

/**
 *
 * @author Le Cao Nguyen
 */
@WebServlet("/oes-admin/report")
@ServletSecurity(@HttpConstraint(rolesAllowed = "testmaster"))
public class ReportController extends ManagedServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Account owner = (Account) request.getSession().getAttribute("currentUser");
        TestManager testManager = new TestManager();
        List<Test> ownedTests = testManager.getTestsByOwner(owner);
        request.setAttribute("ownedTests", ownedTests);
        getCorrespondingViewDispatcher().forward(request, response);
    }
}
