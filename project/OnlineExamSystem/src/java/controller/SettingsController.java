/*
 * Copyright © 2017 Six Idiots Team
 */
package controller;

import controller.login.LoginController;
import dao.CourseManager;
import dao.RoleManager;
import dao.QuestionManager;
import dao.TestManager;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.Course;
import model.Role;
import model.Question;
import model.Test;
import org.hibernate.Hibernate;
import util.servlet.ManagedServlet;

/**
 *
 * @author Hai
 */
@WebServlet("/settings")
public class SettingsController extends ManagedServlet {

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
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("currentUser");
        if (account == null) {
            redirect(response, getServletURL(LoginController.class));
        } else {
            String func = request.getParameter("func");
            if (func == null) {
                request.getRequestDispatcher("/WEB-INF/jsp/error.jsp?error=Permission%20denied").forward(request, response);
                return;
            }
            switch (func) {
                case "manage_test_masters": {
                    RoleManager rm = new RoleManager();
                    Role testmasterRole = rm.getRole("testmaster");
                    Hibernate.initialize(testmasterRole.getAccounts());
                    Set<Account> masters = testmasterRole.getAccounts();
                    request.setAttribute("masters", masters);
                    break;
                }
                case "manage_tests": {
                    TestManager testManager = new TestManager();
                    List<Test> tests = testManager.getTests(account);
                    request.setAttribute("tests", tests);
                    break;
                }
                case "manage_questions": {
                    String courseId = request.getParameter("course");
                    CourseManager cm = new CourseManager();
                    List<Course> courses = cm.getAllCourses();
                    QuestionManager qm = new QuestionManager();
                    List<Question> questions;
                    if (courseId == null || cm.getCourse(courseId) == null) {
                        questions = qm.getQuestions(courses.get(0));
                    } else {
                        questions = qm.getQuestions(cm.getCourse(courseId));
                    }
                    System.out.println(questions.size());
                    request.setAttribute("courses", courses);
                    request.setAttribute("questions", questions);

                }
            }
            request.getRequestDispatcher("/WEB-INF/jsp/" + func + ".jsp?func=" + func).forward(request, response);
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
