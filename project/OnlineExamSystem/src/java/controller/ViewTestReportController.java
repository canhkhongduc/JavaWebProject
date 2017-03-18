/*
 * Copyright © 2017 Six Idiots Team
 */
package controller;

import dao.TestManager;
import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.Attempt;
import model.Test;
import org.hibernate.Hibernate;
import util.servlet.ManagedServlet;

/**
 *
 * @author Niles
 */
@WebServlet("/viewtestreport")
public class ViewTestReportController extends ManagedServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int pageSize = 10;
        String strPageIndex = request.getParameter("page");
        
        if (strPageIndex == null) {
            strPageIndex = "1";
        }
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("currentUser");
//        if (account == null) {
//            response.sendRedirect("oauth2login");
//        } else if (!account.getGroup().getPermissions().stream().anyMatch((Permission t) -> t.getName().equals("manage_tests"))) {
//            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp?error=Permission%20denied").forward(request, response);
//        }

        // Get test id from view
        String testIdStr = request.getParameter("testId");

        // Get info of the test
        TestManager tm = new TestManager();
        Test test = tm.getTest(Long.parseLong(testIdStr));

        if (test != null) {
            session.setAttribute("pageIndex", Integer.parseInt(strPageIndex));

            Hibernate.initialize(test.getAttempts());
            Set<Attempt> attempts = test.getAttempts();

            session.setAttribute("test", test);
            session.setAttribute("attempts", attempts);
            response.sendRedirect("viewtest/report.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

}
