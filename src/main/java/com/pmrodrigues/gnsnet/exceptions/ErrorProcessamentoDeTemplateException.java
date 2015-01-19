package com.pmrodrigues.gnsnet.exceptions;

import javax.mail.MessagingException;

/**
 * Created by Marceloo on 01/10/2014.
 */
public class ErrorProcessamentoDeTemplateException extends RuntimeException {
    public ErrorProcessamentoDeTemplateException(String message, Exception e) {
        super(message,e);
    }
}
