/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.authentication;

import com.github.scribejava.core.model.OAuth2AccessToken;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.googleapi.GoogleProfile;
import util.googleapi.GoogleOAuthService;
import util.HashingUtil;
import util.googleapi.exception.GoogleAPIRequestException;
import util.googleapi.exception.GoogleAPIResponseParseException;
import util.googleapi.exception.TokenExchangeException;
import util.servlet.ServletMethodInterface;

/**
 * Controller for handling callback request from Google's OAuth server.<br>
 * This controller will perform the following functions:<br>
 * <ol>
 * <li>Get authorization <i>code</i> parameter from callback request.</li>
 * <li>Verify <i>state</i> parameter in the callback request (must equal to the
 * SHA-1 hash of session ID).</li>
 * <li>Perform Google API request to get Google public profile.</li>
 * <li>Forward the profile to login controller.</li>
 * </ol>
 *
 * @author nguyen
 */
public class GoogleOAuthCallbackController extends HttpServlet implements ServletMethodInterface {

    private static final long serialVersionUID = -5302619356988689274L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");
        if (code == null) {
            LoginController.responseLoginError(response, LoginResult.OAUTH_ACCESS_DENIED);
            return;
        }

        String state = request.getParameter("state");
        if (state != null) {
            String verifyState = HashingUtil.generateSHA1Hash(request.getSession().getId());
            if (!state.equals(verifyState)) {
                LoginController.responseLoginError(response, LoginResult.OAUTH_INVALID_STATE);
                return;
            }
        }

        try {
            GoogleOAuthService service = new GoogleOAuthService();
            OAuth2AccessToken token = service.exchangeCodeForToken(code);
            GoogleProfile profile = service.getGoogleProfile(token);
            request.setAttribute("googleProfile", profile);
            request.getRequestDispatcher("login").forward(request, response);
        } catch (TokenExchangeException | GoogleAPIRequestException | GoogleAPIResponseParseException ex) {
            LoginController.responseLoginError(response, LoginResult.OAUTH_PROFILE_RETRIEVAL_ERROR);
            Logger.getLogger(GoogleOAuthCallbackController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
