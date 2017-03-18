/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.oesadmin.test;

import dao.TestManager;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.Test;
import util.servlet.ManagedServlet;

/**
 *
 * @author Hai
 */
@WebServlet("/oes-admin/test")
@ServletSecurity(@HttpConstraint(rolesAllowed = {"admin", "testmaster"}))
public class TestController extends ManagedServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        TestManager testManager = new TestManager();
        List<Test> tests = testManager.getAccessibleTests(account);
        System.out.println(tests.size());
        request.setAttribute("tests", tests);
        getCorrespondingViewDispatcher().forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
