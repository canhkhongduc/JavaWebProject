/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.client.test;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import dao.AttemptManager;
import dao.ChoiceManager;
import dao.TestManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.Attempt;
import model.Choice;
import model.Question;
import model.Test;

/**
 *
 * @author Lam
 */
@WebServlet("/client/marking")
public class TestMarkController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");

        double score = 0;
        Account account = (Account) request.getSession().getAttribute("currentUser");
        Date startTime = (Date) request.getSession().getAttribute("testStartTime");
        Date endTime = new Date();

        ChoiceManager choiceManager = new ChoiceManager();
        TestManager testManager = new TestManager();
        AttemptManager attemptManager = new AttemptManager();

        Test test = testManager.getTest(Long.parseLong(request.getParameter("testId")), true);
        ;
        List<Choice> allChoiceList = new ArrayList<>();

        Gson gson = new Gson();
        String choiceList = request.getParameter("choiceList");
        JsonArray choiceListJson = gson.fromJson(choiceList, JsonArray.class);
        List<Long> choiceIdList = new ArrayList<>();
        choiceListJson.forEach((element) -> {
            choiceIdList.add(element.getAsLong());
        });
        Set<Question> questionSet = test.getQuestions();
        
        for (Question question : questionSet) {
            int noOfTrueAnswer = 0;
            int noOfChoice = 0;
            int noOfCorrectChoice = 0;

            List<Choice> choices = choiceManager.getChoices(question);

            for (Choice choice : choices) {
                if (choice.isCorrect()) {
                    noOfTrueAnswer++;
                }
                if (choiceIdList.contains(choice.getId())) {
                    noOfChoice++;
                    allChoiceList.add(choice);
                    if (choice.isCorrect()) {
                        noOfCorrectChoice++;
                    }
                }
            }

            if (noOfChoice <= noOfTrueAnswer) {
                score += (double) noOfCorrectChoice / noOfTrueAnswer;
            }
        }
        
        score = score / questionSet.size() * 10;

        Attempt attempt = new Attempt();
        attempt.setScore(score);
        attempt.setStartTime(startTime);
        attempt.setEndTime(endTime);
        attempt.setExaminee(account);
        attempt.setTest(test);
        for (Choice choice : allChoiceList) {
            attempt.addChoice(choice);
        }

        attemptManager.saveAttempt(attempt);
        long attemptId = attempt.getId();
        
        request.getSession().setAttribute("testStartTime", null);
        request.getSession().setAttribute("questionList", null);
        request.getSession().setAttribute("testId", null);

        response.getWriter().write(new Gson().toJson(attemptId));
    }
}
