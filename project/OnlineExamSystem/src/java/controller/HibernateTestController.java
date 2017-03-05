/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.AccountManager;
import java.io.IOException;
import java.text.DateFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import util.HashingUtil;

/**
 * Controller for testing Hibernate DAO objects.
 *
 * @author nguyen
 */
public class HibernateTestController extends HttpServlet {

    private static final long serialVersionUID = -6086405224915190824L;

    /**
     * Append a line constructed from format string and arguments to response body.
     * @param response The response object.
     * @param format The format string.
     * @param args The arguments to be included.
     * @throws ServletException
     * @throws IOException 
     */
    private void appendToResponse(HttpServletResponse response, String format, Object... args) 
            throws ServletException, IOException {
        response.getWriter().printf("<p>" + format + "</p>", args);
    }
    /**
     * Change password of admin account.
     *
     * @param password The new password
     * @return true if success, otherwise false.
     */
    private boolean changeAdminPassword(String password) {
        AccountManager accountManager = new AccountManager();
        Account admin = accountManager.getAccount("admin");
        admin.setPassword(HashingUtil.generateSHA512Hash("admin"));
        return accountManager.updateAccount(admin);
    }

    /**
     * Print account information.
     * @param username Username of the account
     * @param response The response object.
     * @throws ServletException
     * @throws IOException 
     */
    private void showAccountInfo(String username, HttpServletResponse response) 
            throws ServletException, IOException {
        AccountManager accountManager = new AccountManager();
        if (accountManager.hasAccount(username)) {
            Account a = accountManager.getAccount(username);
            appendToResponse(response, "ID: %s", a.getId());
            appendToResponse(response, "Username: %s", a.getUsername());
            appendToResponse(response, "Full name: %s", a.getFullName());
            appendToResponse(response, "Gender: %s", a.getGender() ? "Male" : "Female");
            appendToResponse(response, "Birthdate: %s", DateFormat.getDateInstance().format(a.getBirthdate()));
            appendToResponse(response, "Email: %s", a.getEmail());
            appendToResponse(response, "Group: %s", a.getGroup().getDescription());
        } else {
            appendToResponse(response, "%s does not exist!", username);
        }
    }

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
        String action = request.getParameter("action");
        switch (action) {
            case "chgpwd":
                String password = request.getParameter("password");
                if (password == null) {
                    password = "admin";
                }
                if (changeAdminPassword(password)) {
                    appendToResponse(response, "Admin password has been changed!");
                } else {
                    appendToResponse(response, "Failed to change admin password!");
                }
                break;
            case "accinfo":
                String username = request.getParameter("username");
                if (username == null) {
                    appendToResponse(response, "No username provided!");
                } else {
                    showAccountInfo(username, response);
                }
                break;
            default:
                appendToResponse(response, "No action specified!");
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