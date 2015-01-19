package com.pmrodrigues.gnsnet.services;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by Marceloo on 01/10/2014.
 */

public interface EmailService {

    EmailService to(String to);

    EmailService subject(String subject);

    EmailService template(String template, Map<String, Object> parameters);

    void send();

    EmailService message(String message);

    EmailService attachment(String filename, InputStream attachment);
}
