/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.dev;

import dao.AccountManager;
import dao.RoleManager;
import java.io.IOException;
import java.util.stream.Collectors;
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
 * @author Le Cao Nguyen
 */
@WebServlet("/dev/sectest")
@ServletSecurity(@HttpConstraint(rolesAllowed = {"admin", "testmaster"}))
public class SecurityTestController extends ManagedServlet {

    private void appendToResponse(HttpServletResponse response, String format, Object... args)
            throws ServletException, IOException {
        response.getWriter().printf("<p>" + format + "</p>\n", args);
    }

    private void doPrintAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().printf("User principal: %s\n", request.getUserPrincipal().getName());
        response.getWriter().printf("Remote user: %s\n", request.getRemoteUser());
        RoleManager roleManager = new RoleManager();
        response.getWriter().printf("Role(s): %s\n",
                roleManager.getAllRoles().stream().map(Role::getName)
                        .filter((roleName) -> request.isUserInRole(roleName))
                        .collect(Collectors.joining(", ")));
    }

    private void doPromoteAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccountManager accountManager = new AccountManager();
        RoleManager roleManager = new RoleManager();
        if (!request.isUserInRole("admin")) {
            appendToResponse(response, "You must be an admin to do this action!");
            return;
        }
        String username = request.getParameter("username");
        if (username == null) {
            appendToResponse(response, "Need 'username' parameter!");
            return;
        }
        Account account = accountManager.getAccount(username);
        if (account == null) {
            appendToResponse(response, "Username does not exist!");
            return;
        }
        if (account.hasRole("admin")) {
            appendToResponse(response, "Admin cannot be promoted to testmaster.");
            return;
        }
        account.setOnlyOneRole(roleManager.getRole("testmaster"));
        if (accountManager.updateAccount(account)) {
            appendToResponse(response, "%s has been promoted to testmaster.", username);
        } else {
            appendToResponse(response, "Failed to promote %s to testmaster.", username);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            doPrintAccount(request, response);
        } else {
            switch (action) {
                case "promote":
                    doPromoteAccount(request, response);
                    break;
                default:
                    appendToResponse(response, "Action not supported!");
            }
        }

    }

}
