package com.pmrodrigues.gnsnet.configuration;

import com.pmrodrigues.gnsnet.security.AuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Marceloo on 27/09/2014.
 */
@Configuration
@EnableWebSecurity
public class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationProvider provider;

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(provider);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        http.authorizeRequests()
            .antMatchers("*.do").authenticated()
            .anyRequest().permitAll()
            .and()
            .formLogin()
            .loginPage("/login.jsp")
            .permitAll()
            .loginProcessingUrl("/j_spring_security_check")
            .usernameParameter("j_username")
            .passwordParameter("j_password")
            .defaultSuccessUrl("/index.do")
            .failureHandler(new AuthenticationFailureHandler() {
                @Override
                public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                    httpServletRequest.setAttribute("error",e.getMessage());
                    RequestDispatcher dispatcher = httpServletRequest.getRequestDispatcher("/login.jsp");
                    dispatcher.forward(httpServletRequest,httpServletResponse);
                }
            })
            .and()
            .logout()
            .logoutSuccessUrl("/login.jsp")
            .permitAll();


    }

}
