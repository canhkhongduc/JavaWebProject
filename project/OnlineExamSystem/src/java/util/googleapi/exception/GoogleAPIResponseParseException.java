package util.googleapi.exception;

/**
 *
 * @author nguyen
 */
public class GoogleAPIResponseParseException extends Exception {

    private static final String EXCEPTION_MESSAGE = "Could not successfully parse the body of the Google API response.";

    public GoogleAPIResponseParseException() {
        super(EXCEPTION_MESSAGE);
    }

    public GoogleAPIResponseParseException(Throwable cause) {
        super(EXCEPTION_MESSAGE, cause);
    }

}
