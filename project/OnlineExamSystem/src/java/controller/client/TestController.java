/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.client;

import dao.ChoiceManager;
import dao.QuestionManager;
import dao.TestManager;
import java.io.IOException;
import java.util.ArrayList;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ChoiceManager choiceManager = new ChoiceManager();
        TestManager testManager = new TestManager();

        List<Question> questionList;
        List<Choice> choiceList = null;
        long testId = (long) request.getSession().getAttribute("testId");

        if (request.getSession().getAttribute("questionList") == null) {
            questionList = new ArrayList<>(testManager.getTest(testId).getQuestions());
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

        getCorrespondingViewDispatcher().forward(request, response);
    }

}
