/*
 * Copyright © 2017 Six Idiots Team
 */
package controller;

import dao.AccountManager;
import dao.CourseManager;
import dao.GroupManager;
import dao.QuestionManager;
import dao.TestManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.Course;
import model.Group;
import model.Permission;
import model.Question;
import model.Test;

/**
 *
 * @author Hai
 */
@WebServlet("/settings")
public class SettingsController extends HttpServlet {

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
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            response.sendRedirect("oauth2login");
        } else {
            ArrayList<Permission> permissions = new ArrayList<>(account.getGroup().getPermissions());
            permissions.sort((Permission o1, Permission o2) -> o1.getName().compareTo(o2.getName()));
            String func = request.getParameter("func");
            if (func == null) {
                func = permissions.get(0).getName();
            }
            if (func == null) {
                request.getRequestDispatcher("/WEB-INF/jsp/error.jsp?error=Permission%20denied").forward(request, response);
                return;
            }
            request.setAttribute("permissions", permissions);
            switch (func) {
                case "manage_test_masters": {
                    AccountManager am = new AccountManager();
                    GroupManager gm = new GroupManager();
                    Group group = gm.getGroup("testmaster");
                    List<Account> masters = am.getAccountsByGroup(group);
                    request.setAttribute("masters", masters);
                    break;
                }
                case "manage_tests": {
                    TestManager testManager = new TestManager();
                    List<Test> tests = testManager.getTestsByAccount(account);
                    request.setAttribute("tests", tests);
                    break;
                }
                case "manage_questions": {
                    String courseId = request.getParameter("course");
                    CourseManager cm = new CourseManager();
                    List<Course> courses = cm.getAllCourse();
                    QuestionManager qm = new QuestionManager();
                    List<Question> questions;
                    if (courseId == null || cm.getCourse(courseId) == null) {
                        questions = qm.getQuestionsByCourse(courses.get(0));
                    } else {
                        questions = qm.getQuestionsByCourse(cm.getCourse(courseId));
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
