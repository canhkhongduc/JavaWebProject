/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.oesadmin.account;

import dao.AccountManager;
import dao.RoleManager;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.Role;
import util.servlet.ManagedServlet;

/**
 *
 * @author Hai
 */
@WebServlet("/oes-admin/account/update")
@ServletSecurity(@HttpConstraint(rolesAllowed = "admin"))
public class AccountUpdateController extends ManagedServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RoleManager roleManager = new RoleManager();
        List<Role> roles = roleManager.getAllRoles();
        Role adminRole = roleManager.getRole("admin");
        roles.remove(adminRole);
        
        AccountManager accountManager = new AccountManager();
        String username = request.getParameter("username");
        if (username == null) {
            response.sendError(400, "No username specified.");
            return;
        }
        Account account = accountManager.getAccount(username);
        if (account == null) {
            response.sendError(400, "Username does not exist.");
            return;
        }
        
        request.setAttribute("account", account);
        request.setAttribute("roles", roles);
        getCorrespondingViewDispatcher().forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccountManager accountManager = new AccountManager();
        RoleManager roleManager = new RoleManager();
        
        // Extract parameters
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String genderStr = request.getParameter("gender");
        String birthDateStr = request.getParameter("birthDate");
        String roleName = request.getParameter("role");
        
        if (username.isEmpty()) username = null;
        if (fullName.isEmpty()) fullName = null;
        if (email.isEmpty()) email = null;
        if (genderStr.isEmpty()) genderStr = null;
        if (birthDateStr.isEmpty()) birthDateStr = null;
        if (roleName.isEmpty()) roleName = null;
        
        // Validation
        if (username == null) {
            response.sendError(400, "No username specified.");
            return;
        }
        if (fullName == null) {
            response.sendError(400, "Full name must not be empty.");
            return;
        }
        Boolean gender = (genderStr == null) ? null : Boolean.parseBoolean(genderStr);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date birthDate = null;
        try {
            birthDate = (birthDateStr == null) ? null : formatter.parse(birthDateStr);
        } catch (ParseException ex) {
            response.sendError(400, "Invalid birth date.");
            return;
        }
        if (roleName == null) {
            response.sendError(400, "No role specified.");
            return;
        }
        Role role = roleManager.getRole(roleName);
        if (role == null) {
            response.sendError(400, "Invalid role specified.");
            return;
        }
        Account account = accountManager.getAccount(username);
        if (account == null) {
            response.sendError(400, "Username does not exist.");
            return;
        }
        
        // Update profile + role
        account.getProfile().setFullName(fullName);
        account.getProfile().setEmail(email);
        account.getProfile().setGender(gender);
        account.getProfile().setBirthdate(birthDate);
        account.setRole(role);
        accountManager.updateAccount(account);
        
        redirect(response, getServletURL(AccountController.class));
    }
}
