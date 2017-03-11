/*
 * Copyright © 2017 Six Idiots Team
 */
package controller;

import dao.AttemptManager;
import dao.TestManager;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.Attempt;
import model.Permission;
import model.Test;
import util.servlet.ManagedServlet;

/**
 *
 * @author Niles
 */
@WebServlet("/viewtestreport")
public class ViewTestReportController extends ManagedServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
//        if (account == null) {
//            response.sendRedirect("oauth2login");
//        } else if (!account.getGroup().getPermissions().stream().anyMatch((Permission t) -> t.getName().equals("manage_tests"))) {
//            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp?error=Permission%20denied").forward(request, response);
//        }
        
        // Get test id from view
        String testId = request.getParameter("testId");
        
        
         // Get info of the test
        TestManager tm = new TestManager();
        Test test = tm.getTest(Integer.parseInt(testId));
        
        // Get a list of attempts related to this test id
        AttemptManager attM = new AttemptManager();
        List<Attempt> attempts = attM.getAttempts(test);
        
        
        if (test != null) {
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
