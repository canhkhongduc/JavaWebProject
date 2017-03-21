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
 * Controller for testing Hibernate DAO objects.
 *
 * @author Le Cao Nguyen
 */
@WebServlet("/dev/util")
@ServletSecurity(@HttpConstraint(rolesAllowed = "admin"))
public class UtilityController extends ManagedServlet {

    /**
     * Append a line constructed from format string and arguments to response
     * body.
     *
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
        String action = request.getParameter("action");
        switch (action) {
            case "geturl":
                String url = request.getParameter("url");
                String resultUrl = getAbsoluteUrl(url);
                appendToResponse(response, resultUrl);
                break;
            case "errorpage":
                int status;
                try {
                    status = Integer.parseInt(request.getParameter("status"));
                } catch (NumberFormatException ex) {
                    status = -1;
                }
                if (status >= 100 && status < 600) {
                    String message = request.getParameter("message");
                    if (message == null) {
                        response.sendError(status);
                    } else {
                        response.sendError(status, message);
                    }
                } else {
                    throw new RuntimeException("Invalid status code.");
                }
                break;
            default:
                appendToResponse(response, "No action specified!");
        }
    }
}
