/*
 * Copyright © 2017 Six Idiots Team
 */
package util.googleapi.exception;

/**
 *
 * @author nguyen
 */
public class GoogleAPIRequestException extends Exception {

    private static final long serialVersionUID = 4434771570966837321L;
    private static final String EXCEPTION_MESSAGE = "Could not successfully perform the Google API Request.";

    public GoogleAPIRequestException() {
        super(EXCEPTION_MESSAGE);
    }

    public GoogleAPIRequestException(Throwable cause) {
        super(EXCEPTION_MESSAGE, cause);
    }

}
