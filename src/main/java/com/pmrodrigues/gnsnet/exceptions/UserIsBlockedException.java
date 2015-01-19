package com.pmrodrigues.gnsnet.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by Marceloo on 27/09/2014.
 */
public class UserIsBlockedException extends AuthenticationException {
    public UserIsBlockedException(String message) {
        super(message);
    }
}
