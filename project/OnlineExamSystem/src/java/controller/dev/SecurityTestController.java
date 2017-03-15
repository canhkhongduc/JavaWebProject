/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.dev;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.servlet.ManagedServlet;

/**
 *
 * @author Le Cao Nguyen
 */
@WebServlet("/dev/sectest")
@ServletSecurity(@HttpConstraint(rolesAllowed = "admin"))
public class SecurityTestController extends ManagedServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().printf("You are logged in as %s\n", request.getUserPrincipal().getName());
    }
    
}
