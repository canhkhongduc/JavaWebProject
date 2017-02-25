package util.googleapi;

import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import util.googleapi.exception.GoogleAPIRequestException;
import util.googleapi.exception.GoogleAPIResponseParseException;
import util.googleapi.exception.TokenExchangeException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author nguyen
 */
public class GoogleOAuthService {

    // TODO: Move OAuth configuration to a .properties file
    // Configuration
    public static final String CLIENT_ID = "194181304067-kg7a9e5fj2oe90loab7u80vhgokktaff.apps.googleusercontent.com";
    public static final String CLIENT_SECRET = "2QyA1L0ZCUPSTC0Js1WwlnqT";
    public static final String CALLBACK_URI = "http://localhost:8080/OnlineExamSystem/oauth2callback";
    public static final String SCOPE = "https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile";
    public static final String PROFILE_REQUEST_URL = "https://www.googleapis.com/userinfo/v2/me";

    // Underlying OAuth service object
    private final OAuth20Service service;

    /**
     * Construct the service.
     */
    public GoogleOAuthService() {
        this.service = new ServiceBuilder()
                .apiKey(CLIENT_ID)
                .apiSecret(CLIENT_SECRET)
                .callback(CALLBACK_URI)
                .responseType("code")
                .scope(SCOPE)
                .build(GoogleApi20.instance());
    }

    /**
     * Get the authorization URL with no state parameter included.
     *
     * @return The authorization URL.
     */
    public String getAuthorizationUrl() {
        return getAuthorizationUrl(null);
    }

    /**
     * Get the authorization URL with an additional <i>state</i> parameter.
     *
     * The <i>state</i> parameter can be used as a protection against CSRF
     * attack (e.g: it may contain the hash value of client's session ID).
     *
     * @param state The value of <i>state</i> parameter.
     * @return The authorization URL.
     */
    public String getAuthorizationUrl(String state) {
        Map<String, String> params = new HashMap<>();
        if (state != null) {
            params.put("state", state);
        }
        return service.getAuthorizationUrl(params);
    }
    
    /**
     * Exchange authorization code for access token, then save it to session
     * scope.
     *
     * @param authorizationCode The authorization code received from the OAuth
     * callback request.
     * @return The access token if successfully exchanged, or null if failed.
     * @throws util.googleapi.exception.TokenExchangeException
     */
    public OAuth2AccessToken exchangeCodeForToken(String authorizationCode)
            throws TokenExchangeException {
        try {
            return service.getAccessToken(authorizationCode);
        } catch (IOException | InterruptedException | ExecutionException ex) {
            throw new TokenExchangeException(ex);
        }
    }

    /**
     * Request Google public profile with the access token provided.
     *
     * @param token The access token.
     * @return The Google public profile received.
     * @throws util.googleapi.exception.GoogleAPIRequestException
     * @throws util.googleapi.exception.GoogleAPIResponseParseException
     */
    public GoogleProfile getGoogleProfile(OAuth2AccessToken token) throws GoogleAPIRequestException, GoogleAPIResponseParseException {
        GoogleProfile profile = null;
        try {
            OAuthRequest request = new OAuthRequest(Verb.GET, PROFILE_REQUEST_URL);
            service.signRequest(token, request);
            Response response = service.execute(request);
            if (response.isSuccessful()) {
                profile = parseGoogleProfileJson(response.getBody());
            }
        } catch (IOException | InterruptedException | ExecutionException ex) {
            throw new GoogleAPIRequestException(ex);
        }
        return profile;
    }

    /**
     * Parse the JSON body of a Google public profile response.
     *
     * @param json The JSON string containing Google public profile.
     * @return The public profile object.
     * @throws util.googleapi.exception.GoogleAPIResponseParseException
     */
    private GoogleProfile parseGoogleProfileJson(String json) throws GoogleAPIResponseParseException {
        try {
            JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
            String id = obj.get("id").getAsString();
            String name = obj.get("name").getAsString().trim();
            String email = obj.get("email").getAsString();
            return new GoogleProfile(id, name, email);
        } catch (Exception ex) {
            throw new GoogleAPIResponseParseException(ex);
        }
    }
}
