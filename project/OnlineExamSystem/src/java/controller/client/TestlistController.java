/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.client;

import dao.ChoiceManager;
import dao.QuestionManager;
import dao.TestManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Choice;
import model.Question;
import model.Test;
import util.servlet.ManagedServlet;

/**
 *
 * @author Le Cao Nguyen
 */
@WebServlet("/client/testlist")
public class TestlistController extends ManagedServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String testId = request.getParameter("id");
        if (testId == null || testId.equals("")) {
            TestManager testManager = new TestManager();

            List<Test> testList = testManager.getAllTests();

            request.setAttribute("testList", testList);
            getCorrespondingViewDispatcher().forward(request, response);
        }else{
            request.getSession().setAttribute("questionList", null);
            request.getSession().setAttribute("testId", Long.parseLong(testId));
            request.getSession().setAttribute("testStartTime", new Date());
            
            response.sendRedirect("test");
        }
    }

}
