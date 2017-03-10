/*
 * Copyright © 2017 Six Idiots Team
 */
package util.googleapi.exception;

/**
 *
 * @author nguyen
 */
public class GoogleAPIResponseParseException extends Exception {

    private static final long serialVersionUID = -7747794990647840142L;
    private static final String EXCEPTION_MESSAGE = "Could not successfully parse the body of the Google API response.";

    public GoogleAPIResponseParseException() {
        super(EXCEPTION_MESSAGE);
    }

    public GoogleAPIResponseParseException(Throwable cause) {
        super(EXCEPTION_MESSAGE, cause);
    }

}
