/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.login;

import com.github.scribejava.core.model.OAuth2AccessToken;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HashingUtil;
import util.googleapi.GoogleOAuthService;
import util.googleapi.GoogleProfile;
import util.googleapi.exception.GoogleAPIRequestException;
import util.googleapi.exception.GoogleAPIResponseParseException;
import util.googleapi.exception.TokenExchangeException;
import util.servlet.ManagedServlet;

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
@WebServlet("/oauth2callback")
public class OAuth2CallbackController extends ManagedServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2CallbackController.class);

    public void redirectLoginError(HttpServletResponse response, LoginError error)
            throws ServletException, IOException {
        redirect(response, getServletURL(LoginErrorController.class), "errorId", error.name().toLowerCase());
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");
        if (code == null) {
            redirectLoginError(response, LoginError.OAUTH_ACCESS_DENIED);
            return;
        }

        String state = request.getParameter("state");
        if (state != null) {
            String verifyState = HashingUtil.generateSHA1Hash(request.getSession().getId());
            if (!state.equals(verifyState)) {
                redirectLoginError(response, LoginError.OAUTH_INVALID_STATE);
                return;
            }
        }

        try {
            GoogleOAuthService service = new GoogleOAuthService();
            OAuth2AccessToken token = service.exchangeCodeForToken(code);
            GoogleProfile profile = service.getGoogleProfile(token);
            request.setAttribute("googleProfile", profile);
            getServletDispatcher(LoginController.class).forward(request, response);
        } catch (TokenExchangeException | GoogleAPIRequestException | GoogleAPIResponseParseException ex) {
            redirectLoginError(response, LoginError.GOOGLE_PROFILE_RETRIEVAL_ERROR);
            LOGGER.error(null, ex);
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
