/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.client.test;

import dao.ChoiceManager;
import dao.QuestionManager;
import dao.TestManager;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Choice;
import model.Question;
import util.servlet.ManagedServlet;

/**
 *
 * @author Le Cao Nguyen
 */
@WebServlet("/client/test")
public class TestController extends ManagedServlet {

    private final long ONE_MINUTE_IN_MILLIS = 60000;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ChoiceManager choiceManager = new ChoiceManager();
        TestManager testManager = new TestManager();
        
        long testLength = (long) request.getSession().getAttribute("testLength");
        Date currentTime = (Date) request.getSession().getAttribute("testStartTime");
        Date endTime = new Date(currentTime.getTime() + testLength * ONE_MINUTE_IN_MILLIS);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
        String endTimeString = dateFormat.format(endTime);

        List<Question> questionList;
        List<Choice> choiceList = null;
        long testId = (long) request.getSession().getAttribute("testId");

        if (request.getSession().getAttribute("questionList") == null) {
            questionList = new ArrayList<>(testManager.getTest(testId, true).getQuestions());
            request.getSession().setAttribute("questionList", questionList);
        } else {
            questionList = (List<Question>) request.getSession().getAttribute("questionList");
        }

        int currentQuestion = 0;

        if (request.getParameter("question") != null) {
            currentQuestion = Integer.parseInt(request.getParameter("question"));
        }

        if (questionList.isEmpty()) {
            //TODO: redirect to error display page (test contains no questions)
        } else {
            choiceList = choiceManager.getChoices(questionList.get(currentQuestion));
        }

        request.setAttribute("questionList", questionList);
        request.setAttribute("questionIndex", currentQuestion);
        request.setAttribute("choiceList", choiceList);
        request.setAttribute("testId", testId);
        request.setAttribute("endTime", endTimeString);

        getCorrespondingViewDispatcher().forward(request, response);
    }

}
