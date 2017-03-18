/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.profile;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import util.servlet.ManagedServlet;

/**
 *
 * @author Le Cao Nguyen
 */
@WebServlet("/profile")
public class ProfileController extends ManagedServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Account currentUser = (Account) request.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            response.sendError(403, "You must login to be able to view profile.");
            return;
        }
        getCorrespondingViewDispatcher().forward(request, response);
    }
    
}
