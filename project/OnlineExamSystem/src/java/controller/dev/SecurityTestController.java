/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.dev;

import dao.RoleManager;
import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Role;
import util.servlet.ManagedServlet;

/**
 *
 * @author Le Cao Nguyen
 */
@WebServlet("/dev/sectest")
@ServletSecurity(
        @HttpConstraint(rolesAllowed = {"admin", "testmaster"}))
public class SecurityTestController extends ManagedServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().printf("User principal: %s\n", request.getUserPrincipal().getName());
        response.getWriter().printf("Remote user: %s\n", request.getRemoteUser());
        RoleManager manager = new RoleManager();
        response.getWriter().printf("Role(s): %s\n",
                manager.getAllRoles().stream().map(Role::getName)
                        .filter((roleName) -> request.isUserInRole(roleName))
                        .collect(Collectors.joining(", ")));
    }

}
