/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.AccountManager;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import org.hibernate.Hibernate;
import util.HashingUtil;

/**
 * Controller for testing Hibernate DAO objects.
 *
 * @author nguyen
 */
public class HibernateTestController extends HttpServlet {

    private static final long serialVersionUID = -6086405224915190824L;

    /**
     * 
     * @param response
     * @param password
     * @throws ServletException
     * @throws IOException 
     */
    private void updateAdminPassword(HttpServletResponse response, String password) throws ServletException, IOException {
        AccountManager accountManager = new AccountManager();
        Account admin = accountManager.getAccount("admin");
        admin.setPassword(HashingUtil.generateSHA512Hash("admin"));
        if (accountManager.updateAccount(admin)) {
            response.getWriter().write("Admin password has been updated!");
        } else {
            response.getWriter().write("Failed to update admin password!");
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
            case "uap":
                String password = request.getParameter("password");
                if (password == null) {
                    password = "admin";
                }
                updateAdminPassword(response, password);
                break;
            default:
                response.getWriter().write("No action has been done!");
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
