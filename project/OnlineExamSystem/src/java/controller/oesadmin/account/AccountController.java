/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.oesadmin.account;

import dao.AccountManager;
import dao.RoleManager;
import java.io.IOException;
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
@WebServlet("/oes-admin/account")
@ServletSecurity(@HttpConstraint(rolesAllowed = "admin"))
public class AccountController extends ManagedServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RoleManager roleManager = new RoleManager();
        List<Role> roles = roleManager.getAllRoles();
        Role adminRole = roleManager.getRole("admin");
        AccountManager accountManager = new AccountManager();
        List<Account> accounts = accountManager.getAllAccounts(true);
        roles.remove(adminRole);
        accounts.removeIf((account) -> account.hasRole(adminRole));
        
        request.setAttribute("accounts", accounts);
        request.setAttribute("roles", roles);
        getCorrespondingViewDispatcher().forward(request, response);
    }
}
