/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.client;

import dao.TestManager;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.Test;
import util.servlet.ManagedServlet;

/**
 *
 * @author Le Cao Nguyen
 */
@WebServlet("/client/dashboard")
public class DashboardController extends ManagedServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Date dateobj = new Date();
        java.sql.Date date = new java.sql.Date(dateobj.getTime());
        System.out.println(date);
        TestManager testManager = new TestManager();
        List<Test> tests = testManager.getIncomingTest(date);
        System.out.println(tests.size());
        System.out.println("fdg");
        request.setAttribute("tests", tests);
        getCorrespondingViewDispatcher().forward(request, response);
    }
}
