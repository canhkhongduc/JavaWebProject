/*
 * Copyright 2017 Le Cao Nguyen
 */
package controller.login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.servlet.ManagedServlet;

/**
 *
 * @author Le Cao Nguyen
 */
@WebServlet("/login/error")
public class LoginErrorController extends ManagedServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String errorId = request.getParameter("errorId");
        if (errorId == null) {
            response.sendError(400);
            return;
        }
        try {
            LoginError error = LoginError.valueOf(errorId.toUpperCase());
            request.setAttribute("message", error.getMessage());
            getCorrespondingViewDispatcher().forward(request, response);
        } catch (IllegalArgumentException ex) {
            response.sendError(400);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
