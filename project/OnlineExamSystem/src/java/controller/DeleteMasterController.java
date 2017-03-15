/*
 * Copyright © 2017 Six Idiots Team
 */
package controller;

import dao.AccountManager;
import dao.RoleManager;
import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.Role;
import org.hibernate.Hibernate;

/**
 *
 * @author Canh Khong Duc
 */
@WebServlet("/deletemaster")
public class DeleteMasterController extends HttpServlet {

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
        String email = request.getParameter("email");
        
        RoleManager rm = new RoleManager();
        Role studentRole = rm.getRole("student");
        Role testmasterRole = rm.getRole("testmaster");
        
        AccountManager am = new AccountManager();
        Account account = am.getAccount(email);
        System.out.println(email);
        account.getRoles().clear();
        account.addRole(studentRole);
        am.updateAccount(account);
        
        Hibernate.initialize(testmasterRole.getAccounts());
        Set<Account> masters = testmasterRole.getAccounts();
        
        HttpSession session = request.getSession();
        session.setAttribute("master", account);
        session.setAttribute("masters", masters);
        response.sendRedirect("listmaster");
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
