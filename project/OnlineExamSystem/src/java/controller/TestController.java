/*
 * Copyright © 2017 Six Idiots Team
 */
package controller;

import dao.ChoiceManager;
import dao.CourseManager;
import dao.QuestionManager;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.Choice;
import model.Course;
import model.Permission;
import model.Question;

/**
 *
 * @author Lam
 */
@WebServlet("/test")
public class TestController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            response.sendRedirect("oauth2login");
        } else if (!account.getGroup().getPermissions().stream().anyMatch((Permission t) -> t.getName().equals("manage_tests"))) {
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp?error=Permission%20denied").forward(request, response);
        } else {
            String action = request.getParameter("action");
            if (action == null) {
                QuestionManager questionManager = new QuestionManager();
                ChoiceManager choiceManager = new ChoiceManager();

                int currentQuestion = 0;

                if (request.getParameter("question") != null) {
                    currentQuestion = Integer.parseInt(request.getParameter("question"));
                }

                List<Question> questionList = questionManager.getAllQuestions();
                List<Choice> choiceList = choiceManager.getAllChoice(questionList.get(currentQuestion));

                request.setAttribute("questionList", questionList);
                request.setAttribute("questionIndex", currentQuestion);
                request.setAttribute("choiceList", choiceList);

                request.getRequestDispatcher("test.jsp").forward(request, response);
            } else {
                switch (action) {
                    case "add": {
                        List<Course> courses = new CourseManager().getAllCourse();
                        request.setAttribute("courses", courses);
                        request.getRequestDispatcher("/WEB-INF/jsp/add_test.jsp").forward(request, response);
                        break;
                    }
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        QuestionManager questionManager = new QuestionManager();
        ChoiceManager choiceManager = new ChoiceManager();

        int currentQuestion = 0;

        if (request.getParameter("question") != null) {
            currentQuestion = Integer.parseInt(request.getParameter("question"));
        }

        List<Question> questionList = questionManager.getAllQuestions();
        List<Choice> choiceList = choiceManager.getAllChoice(questionList.get(currentQuestion));

        request.setAttribute("questionList", questionList);
        request.setAttribute("questionIndex", currentQuestion);
        request.setAttribute("choiceList", choiceList);

        request.getRequestDispatcher("test.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
