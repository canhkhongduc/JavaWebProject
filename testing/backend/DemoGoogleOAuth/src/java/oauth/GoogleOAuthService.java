package oauth;

import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import model.GoogleProfile;

/**
 *
 * @author nguyen
 */
public class GoogleOAuthService {
    // Configuration
    public static final String TOKEN_ATTRIBUTE_NAME = "oauth_token";
    public static final String CLIENT_ID = "841821563930-fov210qvc7oaa4aftjefdqflkssk4djv.apps.googleusercontent.com";
    public static final String CLIENT_SECRET = "QT7R0kZ5VvvZnYWBELybQjLQ";
    public static final String CALLBACK_URI = "http://localhost:8080/DemoGoogleOAuth/oauth2callback";
    public static final String SCOPE = "https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile";
    public static final String PROFILE_REQUEST_URL = "https://www.googleapis.com/userinfo/v2/me";
    public static final boolean OFFLINE_ACCESS = false;

    // Singleton instance
    private static GoogleOAuthService instance = null;

    // Properties
    private final OAuth20Service service;
    
    private GoogleOAuthService() {
        ServiceBuilder builder = new ServiceBuilder();
        this.service = builder
                .apiKey(CLIENT_ID)
                .apiSecret(CLIENT_SECRET)
                .callback(CALLBACK_URI)
                .responseType("code")
                .scope(SCOPE)
                .build(GoogleApi20.instance());
    }

    public static GoogleOAuthService getInstance() {
        if (instance == null) {
            synchronized (GoogleOAuthService.class) {
                instance = new GoogleOAuthService();
            }
        }
        return instance;
    }

    private Map<String, String> buildAdditionalParams() {
        Map<String, String> params = new HashMap<>();
        if (OFFLINE_ACCESS) {
            params.put("access_type", "offline");
        }
        /*
            params.put("include_granted_scopes", "true");
            params.put("prompt", "consent");
        */
        return params;
    }

    private GoogleProfile parseGoogleProfileJson(String json) {
        JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
        String id = obj.get("id").getAsString();
        String name = obj.get("name").getAsString();
        String email = obj.get("email").getAsString();
        return new GoogleProfile(id, name, email);
    }

    public String getAuthorizationUrl() {
        return getAuthorizationUrl(null);
    }

    public String getAuthorizationUrl(String state) {
        Map<String, String> params = buildAdditionalParams();
        if (state != null) {
            params.put("state", state);
        }
        return service.getAuthorizationUrl(params);
    }

    public void retrieveAndSaveToken(String authorizationCode, HttpSession session) {
        try {
            OAuth2AccessToken token = service.getAccessToken(authorizationCode);
            session.setAttribute(TOKEN_ATTRIBUTE_NAME, token);
        } catch (IOException | InterruptedException | ExecutionException ex) {
            Logger.getLogger(GoogleOAuthService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public OAuth2AccessToken getAccessToken(HttpSession session) {
        OAuth2AccessToken token = null;
        try {
            token = (OAuth2AccessToken) session.getAttribute(TOKEN_ATTRIBUTE_NAME);
            if (token != null && OFFLINE_ACCESS) {
                token = service.refreshAccessToken(token.getRefreshToken());
                session.setAttribute(TOKEN_ATTRIBUTE_NAME, token);
            }
        } catch (IOException | InterruptedException | ExecutionException ex) {
            Logger.getLogger(GoogleOAuthService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return token;
    }

    public GoogleProfile getGoogleProfile(OAuth2AccessToken token) {
        GoogleProfile profile = null;
        try {
            OAuthRequest request = new OAuthRequest(Verb.GET, PROFILE_REQUEST_URL);
            service.signRequest(token, request);
            Response response = service.execute(request);
            profile = parseGoogleProfileJson(response.getBody());
        } catch (IOException | InterruptedException | ExecutionException ex) {
            Logger.getLogger(GoogleOAuthService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return profile;
    }
}
