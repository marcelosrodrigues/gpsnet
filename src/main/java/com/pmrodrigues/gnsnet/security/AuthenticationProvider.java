package com.pmrodrigues.gnsnet.security;

import com.pmrodrigues.gnsnet.models.Usuario;
import com.pmrodrigues.gnsnet.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Created by Marceloo on 27/09/2014.
 */
@Service
public class AuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {

    @Autowired
    private UserService service;

    private static final Logger logging = Logger.getLogger(AuthenticationProvider.class);

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        logging.debug("tentando efetuar a autenticação de um usuário");

        final String email = (String) authentication.getPrincipal();
        final String password = (String) authentication.getCredentials();

        final UserAuthentication authenticated = new UserAuthentication(service.doAuthentication(email,password));
        return authenticated;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
