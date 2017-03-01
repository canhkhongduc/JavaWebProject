package controller.authentication;

/**
 *
 * @author nguyen
 */
public enum LoginResult {
    SUCCESS,
    UNKNOWN_ERROR,
    BAD_REQUEST,
    DOMAIN_NOT_ALLOWED,
    USERNAME_DOES_NOT_EXIST,
    INCORRECT_PASSWORD,
    OAUTH_INVALID_STATE,
    OAUTH_ACCESS_DENIED,
    OAUTH_PROFILE_RETRIEVAL_ERROR;
    
    public String getMessage() {
        switch (this) {
            case SUCCESS:
                return "Successful.";
            case BAD_REQUEST:
                return "Request does not contain valid login credential.";
            case USERNAME_DOES_NOT_EXIST:
                return "Username does not exist";
            case INCORRECT_PASSWORD:
                return "Incorrect password";
            case DOMAIN_NOT_ALLOWED:
                return "The domain of username is not allowed.";
            default:
                return "";
        }
    }
}
