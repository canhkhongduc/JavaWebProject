/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.googleapi.GoogleOAuthService;
import util.HashingUtil;
import util.servlet.ManagedServlet;

/**
 *
 * @author nguyen
 */
@WebServlet("/oauth2login")
public class OAuth2LoginController extends ManagedServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        GoogleOAuthService service = new GoogleOAuthService();
        String state = HashingUtil.generateSHA1Hash(request.getSession().getId());
        redirect(response, service.getAuthorizationUrl(state));
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
