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
import model.Account;
import model.Test;
import util.servlet.ManagedServlet;

/**
 *
 * @author Hai
 */
@WebServlet("/oes-admin/test")
@ServletSecurity(@HttpConstraint(rolesAllowed = "testmaster"))
public class TestController extends ManagedServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("currentUser");
        TestManager testManager = new TestManager();
        List<Test> tests = testManager.getAccessibleTests(account);
        request.setAttribute("tests", tests);
        getCorrespondingViewDispatcher().forward(request, response);
    }
}
