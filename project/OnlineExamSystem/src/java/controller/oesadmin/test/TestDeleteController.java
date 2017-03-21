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
import util.servlet.ManagedServlet;

/**
 *
 * @author Hai
 */
@WebServlet("/oes-admin/test/delete")
@ServletSecurity(@HttpConstraint(rolesAllowed = "testmaster"))
public class TestDeleteController extends ManagedServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TestManager testManager = new TestManager();
        String testId = request.getParameter("id");
        testManager.deleteTest(testManager.getTest(Long.parseLong(testId)));
        redirect(response, getServletURL(TestController.class));
    }
}
