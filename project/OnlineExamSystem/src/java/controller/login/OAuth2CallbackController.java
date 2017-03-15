/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.login;

import com.github.scribejava.core.model.OAuth2AccessToken;
import java.io.IOException;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
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
@WebServlet(urlPatterns = "/oauth2callback", asyncSupported = true)
public class OAuth2CallbackController extends ManagedServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2CallbackController.class);

    public void redirectLoginError(HttpServletResponse response, LoginError error)
            throws ServletException, IOException {
        response.sendError(400, "Error while logging in: " + error.getMessage());
    }

    private boolean isValidDomain(String email) {
        // TODO: Move allowed domain to controller parameter or application configuration
        return email.endsWith("@fpt.edu.vn");
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

        AsyncContext context = request.startAsync();
        context.start(() -> {
            HttpServletRequest req = (HttpServletRequest) context.getRequest();
            HttpServletResponse res = (HttpServletResponse) context.getResponse();
            try {
                GoogleOAuthService service = new GoogleOAuthService();
                OAuth2AccessToken token = service.exchangeCodeForToken(code);
                GoogleProfile profile = service.getGoogleProfile(token);
                if (isValidDomain(profile.getEmail())) {
                    req.setAttribute("googleProfile", profile);
                    HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(req) {
                        @Override
                        public String getMethod() {
                            return "POST";
                        }
                    };
                    getServletDispatcher(LoginController.class).forward(wrapper, res);
                } else {
                    redirectLoginError(res, LoginError.DOMAIN_NOT_ALLOWED);
                }
            } catch (TokenExchangeException | GoogleAPIRequestException | GoogleAPIResponseParseException ex) {
                try {
                    redirectLoginError(res, LoginError.GOOGLE_PROFILE_RETRIEVAL_ERROR);
                } catch (ServletException | IOException ex1) {
                    LOGGER.error(null, ex1);
                }
                LOGGER.error(null, ex);
            } catch (ServletException | IOException ex) {
                LOGGER.error(null, ex);
            }
            context.complete();
        });

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
