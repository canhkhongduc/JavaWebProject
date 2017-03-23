/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.client.test;

import dao.AttemptManager;
import dao.TestManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
@WebServlet("/client/test/list")
@ServletSecurity(@HttpConstraint(rolesAllowed = "student"))
public class TestListController extends ManagedServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String testId = request.getParameter("id");
        Account account = (Account) request.getSession().getAttribute("currentUser");

        AttemptManager attemptManager = new AttemptManager();
        List<Integer> attemptList = new ArrayList<>();

        if (testId == null || testId.equals("")) {
            TestManager testManager = new TestManager();

            List<Test> testList2 = testManager.getAllTests(true);
            List<Test> testList = new ArrayList<>();
            for (Test test : testList2) {
                if (test.getExaminees().contains(account)
                        || !test.getRestricted()) {
                    testList.add(test);
                    attemptList.add(attemptManager.
                            getAttemptsByTestAndExaminee(test, account).size());
                }
            }

            request.setAttribute("attemptList", attemptList);
            request.setAttribute("testList", testList);
            getCorrespondingViewDispatcher().forward(request, response);
        } else {
            TestManager testManager = new TestManager();
            Test test = testManager.getTest(Long.parseLong(testId), true);
            int currentAttempts = attemptManager.
                    getAttemptsByTestAndExaminee(test, account, true).size();

            if (test.getAttemptLimit() <= currentAttempts) {
                response.sendError(400,
                        "Sorry, you haved passed the number of attempt limit");
                return;
            }

            Date today = new Date();

            if (today.before(test.getJoinEndTime()) && today.after(test.getJoinStartTime())) {
                request.getSession().setAttribute("questionList", null);
                if (request.getSession().getAttribute("testLength") == null
                        && request.getSession().getAttribute("testStartTime") == null
                        && request.getSession().getAttribute("testId") == null) {
                    request.getSession().setAttribute("testStartTime", new Date());
                    request.getSession().setAttribute("testId", Long.parseLong(testId));
                    request.getSession().setAttribute("testLength", Long.valueOf(test.getTimeLength()));
                } else if (Long.parseLong(testId) != (long) request.getSession().getAttribute("testId")) {
                    Long currentTestId = (Long) request.getSession().getAttribute("testId");
                    
                    Test currentTest = testManager.getTest(currentTestId, true);

                    String courseName = currentTest.getCourse() != null ? test.getCourse().getName() : "null";
                    response.sendError(400,
                            "Sorry, you have unsubmitted test, "
                            + "please submit your test first before"
                            + " enter new test <br>(Test name = "
                            + currentTest.getName() + ", Course = "
                            + courseName + ")<br>");
                    return;
                }
                redirect(response, getServletURL(TestController.class));
            } else {
                response.sendError(400, "Sorry, this test is unavailable at this time");
            }
        }
    }

}
