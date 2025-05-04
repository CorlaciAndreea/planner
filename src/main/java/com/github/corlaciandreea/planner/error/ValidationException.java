package com.github.corlaciandreea.planner.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

/**
 * Class that handles the exceptions that might appear in the application.
 */
public class ValidationException extends HttpServerErrorException {
    /**
     * Constructor.
     *
     * @param message the message to be displayed.
     */
    public ValidationException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
