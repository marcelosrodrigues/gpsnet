package com.pmrodrigues.gnsnet.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Marceloo on 21/01/2015.
 */
public class AuthenticationFailure implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletRequest.setAttribute("error",e.getMessage());
        RequestDispatcher dispatcher = httpServletRequest.getRequestDispatcher("/login.jsp");
        dispatcher.forward(httpServletRequest,httpServletResponse);
    }
}
