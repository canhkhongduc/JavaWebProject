/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.login;

/**
 *
 * @author nguyen
 */
public enum LoginError {
    NONE,
    UNKNOWN_ERROR,
    BAD_REQUEST,
    DOMAIN_NOT_ALLOWED,
    USERNAME_DOES_NOT_EXIST,
    INCORRECT_PASSWORD,
    OAUTH_INVALID_STATE,
    OAUTH_ACCESS_DENIED,
    GOOGLE_PROFILE_RETRIEVAL_ERROR;

    public String getMessage() {
        switch (this) {
            case NONE:
                return "Successful.";
            case BAD_REQUEST:
                return "Request does not contain valid login credential.";
            case USERNAME_DOES_NOT_EXIST:
                return "Username does not exist.";
            case INCORRECT_PASSWORD:
                return "Incorrect password.";
            case DOMAIN_NOT_ALLOWED:
                return "The domain of the username is not allowed.";
            case OAUTH_INVALID_STATE:
                return "Invalid OAuth state returned.";
            case OAUTH_ACCESS_DENIED:
                return "Could not access Google's OAuth service.";
            case GOOGLE_PROFILE_RETRIEVAL_ERROR:
                return "Could not retrieve Google profile.";
            default:
                return "Unknown error.";
        }
    }
}
