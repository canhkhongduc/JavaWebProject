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
 *
 * @author nguyen
 */
public class GoogleOAuthCallbackController extends HttpServlet implements ServletMethodInterface {

    private void responseGoogleOAuthError(HttpServletResponse response, int status, String error)
            throws ServletException, IOException {
        response.sendError(status, String.format("Google sign-in error: %s", (error == null) ? "unknown" : error));
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");
        if (code == null) {
            String error = request.getParameter("error");
            responseGoogleOAuthError(response, HttpServletResponse.SC_BAD_REQUEST, error);
            return;
        }

        String state = request.getParameter("state");
        if (state != null) {
            String verifyState = HashingUtil.generateSHA256Hash(request.getSession().getId());
            if (!state.equals(verifyState)) {
                responseGoogleOAuthError(response, HttpServletResponse.SC_BAD_REQUEST, "invalid_request_state");
            }
        }

        try {
            GoogleOAuthService service = new GoogleOAuthService();
            OAuth2AccessToken token = service.exchangeCodeForToken(code);
            GoogleProfile profile = service.getGoogleProfile(token);
            request.setAttribute("google_profile", profile);
            request.getRequestDispatcher("login").forward(request, response);
        } catch (TokenExchangeException ex) {
            responseGoogleOAuthError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "server_token_exchange_error");
            Logger.getLogger(GoogleOAuthCallbackController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GoogleAPIRequestException | GoogleAPIResponseParseException ex) {
            responseGoogleOAuthError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "server_profile_retrieval_error");
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
